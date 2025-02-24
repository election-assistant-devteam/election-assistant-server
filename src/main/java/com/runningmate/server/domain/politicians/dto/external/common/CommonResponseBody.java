package com.runningmate.server.domain.politicians.dto.external.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CommonResponseBody<T> {
    @JsonProperty("header")
    private CommonHeader header;

    @JsonProperty("body")
    private CommonBody<T> body;
}
