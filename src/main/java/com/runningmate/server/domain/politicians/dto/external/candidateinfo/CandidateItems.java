package com.runningmate.server.domain.politicians.dto.external.candidateinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateItems {
    @JsonProperty("item")
    private List<CandidateItem> itemList;
}
