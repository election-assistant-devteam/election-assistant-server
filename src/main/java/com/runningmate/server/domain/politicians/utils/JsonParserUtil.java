package com.runningmate.server.domain.politicians.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.politicians.dto.external.common.CommonApiResponse;
import com.runningmate.server.domain.politicians.dto.external.common.CommonResponseBody;
import com.runningmate.server.domain.politicians.exception.ParsingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.PARSING_FAILED;

@Component
@RequiredArgsConstructor
public class JsonParserUtil {
    private final ObjectMapper objectMapper;

    public <T extends CommonResponseBody<?>> CommonApiResponse<T> parseJson(String responseBody, Class<T> bodyClass) {
        try {
            return objectMapper.readValue(responseBody, objectMapper.getTypeFactory()
                    .constructParametricType(CommonApiResponse.class, bodyClass));
        } catch (JsonProcessingException e) {
            throw new ParsingFailedException(PARSING_FAILED);
        }
    }
}
