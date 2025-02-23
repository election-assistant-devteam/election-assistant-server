package com.runningmate.server.domain.politicians.dto.external.candidateinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateApiResponse {
    @JsonProperty("response")
    private CandidateResponse candidateResponse;
}
