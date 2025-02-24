package com.runningmate.server.domain.politicians.dto.external.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonApiResponse<T extends CommonResponseBody<?>> {
    @JsonProperty("response")
    private T response;
}
