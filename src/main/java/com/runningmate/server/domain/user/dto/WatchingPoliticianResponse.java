package com.runningmate.server.domain.user.dto;

import com.runningmate.server.domain.politicians.model.Politician;

public record WatchingPoliticianResponse(
        String party,
        String name
) {
    public static WatchingPoliticianResponse entityToDto(Politician entity){
        return new WatchingPoliticianResponse(entity.getParty(), entity.getName());
    }
}
