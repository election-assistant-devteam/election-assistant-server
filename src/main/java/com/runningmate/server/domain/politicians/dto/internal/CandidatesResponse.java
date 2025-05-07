package com.runningmate.server.domain.politicians.dto.internal;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CandidatesResponse {
    private List<CandidateSimpleInfo> candidates;
    private Integer lastId;
    private Boolean hasMore;
}
