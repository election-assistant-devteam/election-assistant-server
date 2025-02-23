package com.runningmate.server.domain.politicians.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateApiResponse;
import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.PARSING_FAILED;

@Slf4j
@Component
public class CandidateUtil {

    private final String baseUrl = "http://apis.data.go.kr/9760000/PofelcddInfoInqireService";
    private final ObjectMapper objectMapper = new ObjectMapper();
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
            CandidateApiResponse response2 = getCandidateApiResponse(responseBody);

            if (response2.getCandidateResponse() != null && response2.getCandidateResponse().getBody() != null) { // code
                results.addAll(response2.getCandidateResponse().getBody().getItems().getItemList());
                pageNo++;
            } else {
                hasMoreData = false;
            }


            log.info("{}", responseEntity);
        }

        return results;
    }

    private CandidateApiResponse getCandidateApiResponse(String responseBody) {
        CandidateApiResponse response;
        try {
            response = objectMapper.readValue(responseBody, CandidateApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new ParsingFailedException(PARSING_FAILED);
        }
        return response;
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
}
