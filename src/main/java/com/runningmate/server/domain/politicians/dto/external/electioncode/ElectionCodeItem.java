package com.runningmate.server.domain.politicians.dto.external.electioncode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectionCodeItem {

    @JsonProperty("num")
    private int num;

    @JsonProperty("sgId")
    private String sgId;

    @JsonProperty("sgName")
    private String sgName;

    @JsonProperty("sgTypecode")
    private String sgTypecode;

    @JsonProperty("sgVotedate")
    private String sgVotedate;
}
