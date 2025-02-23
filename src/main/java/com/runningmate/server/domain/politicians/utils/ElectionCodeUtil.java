package com.runningmate.server.domain.politicians.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeApiResponse;
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
public class ElectionCodeUtil {

    private final String baseUrl = "http://apis.data.go.kr/9760000/CommonCodeService";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${runningmate.api.publicData.key}")
    private String publicDataKey;

    public List<ElectionCodeItem> fetchElectionCodes() {
        RestClient restClient = RestClient.create();
        int pageNo = 1;
        List<ElectionCodeItem> results = new ArrayList<>();
        boolean hasMoreData = true;

        String responseBody = null;
        while(hasMoreData) {
            // HTTP 헤더 설정
            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl)
                    .path("/getCommonSgCodeList")
                    .queryParam("serviceKey", publicDataKey)
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

            responseBody = responseEntity.getBody();
            log.info(responseBody);

            // Json 파싱
            // JSON을 Java 객체로 변환
            ElectionCodeApiResponse response = getElectionCodeApiResponse(responseBody);

            // items 태그가 있고, 리스트가 비어있지 않으면 저장
            log.info("{}", response);
            if (response.getElectionCodeResponseBody() != null && response.getElectionCodeResponseBody().getBody() != null) {
                results.addAll(response.getElectionCodeResponseBody().getBody().getItems().getItemList());
                pageNo++;
                log.info("pageNo {}", pageNo);
            } else {
                hasMoreData = false;
            }

            //log.info("{}", responseEntity);
        }

        // 응답 값
        return results;
    }

    private ElectionCodeApiResponse getElectionCodeApiResponse(String responseBody) {
        ElectionCodeApiResponse response = null;
        try {
            response = objectMapper.readValue(responseBody, ElectionCodeApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new ParsingFailedException(PARSING_FAILED);
        }
        return response;
    }


    public List<ElectionCodeItem> getFilteredElectionCodeItems(List<ElectionCodeItem> response) {
        List<ElectionCodeItem> filteredByNationalAndYear = new ArrayList<>();
        for (var electionCodeItem : response) {
            Integer year = Integer.parseInt(electionCodeItem.getSgVotedate().substring(0, 4));
            if (year >= 2024
                    && electionCodeItem.getSgName().contains("국회의원")) {
                filteredByNationalAndYear.add(electionCodeItem);
            }
        }
        return filteredByNationalAndYear;
    }
}
