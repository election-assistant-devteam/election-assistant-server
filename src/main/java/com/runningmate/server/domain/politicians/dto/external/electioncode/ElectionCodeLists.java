package com.runningmate.server.domain.politicians.dto.external.electioncode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectionCodeLists {
    @JsonProperty("item")
    private List<ElectionCodeItem> itemList;
}
