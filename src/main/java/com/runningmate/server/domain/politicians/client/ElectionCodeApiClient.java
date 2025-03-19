package com.runningmate.server.domain.politicians.client;

import com.runningmate.server.domain.politicians.dto.external.common.CommonApiResponse;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeResponseBody;
import com.runningmate.server.domain.politicians.utils.JsonParserUtil;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionCodeApiClient {

    private final String baseUrl = "http://apis.data.go.kr/9760000/CommonCodeService";

    private final JsonParserUtil jsonParserUtil;
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
            //ElectionCodeApiResponse response = getElectionCodeApiResponse(responseBody);
            // JSON을 공통 응답 구조로 변환
            CommonApiResponse<ElectionCodeResponseBody> response = jsonParserUtil.parseJson(responseBody, ElectionCodeResponseBody.class);

            // items 태그가 있고, 리스트가 비어있지 않으면 저장
            log.info("{}", response);
            if (response.getResponse() != null && response.getResponse().getBody() != null) {
                results.addAll(response.getResponse().getBody().getItems().getItemList());
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

}
