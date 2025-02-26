package com.runningmate.server.domain.politicians.dto.external.candidateinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.runningmate.server.domain.politicians.dto.external.common.CommonResponseBody;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateResponse extends CommonResponseBody<CandidateItems> {
}
