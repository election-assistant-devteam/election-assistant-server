package com.runningmate.server.domain.politicians.dto.internal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CandidateSimpleInfo {
    private Integer id;
    private String name;
    private String party;
    private String imageUrl;
}
