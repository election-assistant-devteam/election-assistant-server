package com.runningmate.server.domain.politicians.dto.external.electioncode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectionCodeResponseBody {

    @JsonProperty("header")
    private ElectionCodeHeader header;

    @JsonProperty("body")
    private ElectionCodeBody body;
}
