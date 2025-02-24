package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateResponse;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.dto.external.common.CommonApiResponse;
import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateUtil {

    private final CandidateRepository candidateRepository;

    private final String baseUrl = "http://apis.data.go.kr/9760000/PofelcddInfoInqireService";
    private final JsonParserUtil jsonParserUtil;
    @Value("${runningmate.api.publicData.key}")
    private String publicDataKey;

    public List<CandidateItem> requestCndaInfo(int electionCode, int type){
        RestClient restClient = RestClient.create();
        int pageNo = 1;
        List<CandidateItem> results = new ArrayList<>();
        boolean hasMoreData = true;

        while(hasMoreData) {

            // HTTP 헤더 설정
            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl)
                    .path("/getPofelcddRegistSttusInfoInqire")
                    .queryParam("ServiceKey", publicDataKey)
                    .queryParam("sgId", electionCode) //20220601
                    .queryParam("sgTypecode", type) // 8
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", 100)
                    .queryParam("resultType", "json")
                    .build(true)
                    .toUri();

            // 요청
            ResponseEntity<String> responseEntity = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(String.class);

            String responseBody = responseEntity.getBody();
            log.info("candidate. {}", responseBody);

            // Json을 객체로 변환
            //CandidateApiResponse response2 = getCandidateApiResponse(responseBody);
            CommonApiResponse<CandidateResponse> response2 = jsonParserUtil.parseJson(responseBody, CandidateResponse.class);


            if (response2.getResponse() != null && response2.getResponse().getBody() != null) { // code
                results.addAll(response2.getResponse().getBody().getItems().getItemList());
                pageNo++;
            } else {
                hasMoreData = false;
            }


            log.info("{}", responseEntity);
        }

        return results;
    }

    public List<CandidateItem> fetchCandidateInfo(List<ElectionCodeItem> filteredByNationalAndYear) {
        List<CandidateItem> allCandidateItems = new ArrayList<>();
        for (var electionCodeItem : filteredByNationalAndYear) {
            Integer electionId = Integer.parseInt(electionCodeItem.getSgId());
            Integer electionType = Integer.parseInt(electionCodeItem.getSgTypecode());
            List<CandidateItem> candidateItems = requestCndaInfo(electionId, electionType);
            allCandidateItems.addAll(candidateItems);

            //log.info("{}", candidateItems.size()); //699
        }
        //log.info("itemSize {}", allCandidateItems.size()); // 953
        return allCandidateItems;
    }

    public void saveCandidate(CandidateItem allCandidateItem, Politician politician, Election election) {
        if (checkDuplicateCandidate(politician, election)) return;
        Candidate candidate = Candidate.from(politician, election, allCandidateItem.getJdName());
        candidateRepository.save(candidate);
    }

    private boolean checkDuplicateCandidate(Politician politician, Election election) {
        Optional<Candidate> foundCandidate = candidateRepository.findByPoliticianAndElection(politician, election);
        if (foundCandidate.isPresent()) {
            return true;
        }
        return false;
    }
}
