package com.runningmate.server.domain.politicians.dto.internal;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@Builder
public class CandidatesResponse {
    private ArrayList<CandidateSimpleInfo> candidates;
    private Integer lastId;
    private Boolean hasMore;
}
