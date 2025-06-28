package com.runningmate.server.domain.watch.dto;

import com.runningmate.server.domain.politicians.model.Politician;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WatchedPoliticianResponse {
    private Long id;
    private String name;
    private String party;
    private String imageUrl;

    public static WatchedPoliticianResponse from(Politician politician) {
        return WatchedPoliticianResponse.builder()
                .id(politician.getId())
                .name(politician.getName())
                .party(politician.getParty())
                .imageUrl(politician.getImageUrl())
                .build();
    }
}