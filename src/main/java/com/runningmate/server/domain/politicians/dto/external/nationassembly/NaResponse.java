package com.runningmate.server.domain.politicians.dto.external.nationassembly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaResponse {
    @JsonProperty("head")
    private List<NaHeader> headList;

    @JsonProperty("row")
    private List<NaRow> rowList;
}
