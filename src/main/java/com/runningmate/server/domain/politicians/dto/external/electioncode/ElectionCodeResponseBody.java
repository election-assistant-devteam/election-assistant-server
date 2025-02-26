package com.runningmate.server.domain.politicians.dto.external.electioncode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.runningmate.server.domain.politicians.dto.external.common.CommonResponseBody;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectionCodeResponseBody extends CommonResponseBody<ElectionCodeLists> {
}
