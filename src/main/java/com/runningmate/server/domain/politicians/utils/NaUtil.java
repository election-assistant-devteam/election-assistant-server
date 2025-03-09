package com.runningmate.server.domain.politicians.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaResponse;
import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.dto.external.nationassembly.NationalAssemblyApiResponse;
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
public class NaUtil {
    private final String baseUrl = "https://open.assembly.go.kr/portal/openapi/ALLNAMEMBER";

    @Value("${runningmate.api.openNationalAssemblyInfo.key}")
    private String openNationalKey;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<NaRow> fetchNaInfo(){
        RestClient restClient = RestClient.create();
        List<NaRow> results = new ArrayList<>();
        int pageNo = 1;
        // HTTP 헤더 설정
        while(true) {
            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl)
                    .queryParam("KEY", openNationalKey)
                    .queryParam("Type", "json")
                    .queryParam("pIndex", pageNo++)
                    .queryParam("pSize", 300)
                    .build(true)
                    .toUri();

            // 요청
            ResponseEntity<String> responseEntity = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(String.class);

            String responseBody = responseEntity.getBody();

            // 파싱
            NationalAssemblyApiResponse response = null;
            try {
                response = objectMapper.readValue(responseBody, NationalAssemblyApiResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // null 체크
            if (response == null || response.getAllNaMember() == null) {
                log.info("response.getAllNaMember() size = 0");
                break;
            }

            // size()` 호출 전에 null 체크 추가 - 디버깅용
            //debugResponseSize(response);


            // 올바른 rowList 가져오기 & Null 체크
            if (response.getAllNaMember().size() > 1 && response.getAllNaMember().get(1).getRowList() != null) {
                results.addAll(response.getAllNaMember().get(1).getRowList());
                //log.info("result size {}", results.size());
            } else {
                break;
            }


        }

        return results;
    }

    private static void debugResponseSize(NationalAssemblyApiResponse response) {
        if (response.getAllNaMember().size() > 1) {
            NaResponse naResponse = response.getAllNaMember().get(1);

            if (naResponse.getRowList() != null && !naResponse.getRowList().isEmpty()) {
                log.info("rowList size {}", naResponse.getRowList().size());
            } else {
                log.info("rowList size = 0");
            }
        } else {
            log.info("ALLNAMEMBER have not enough data");
        }
    }
}
