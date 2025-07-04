package com.runningmate.server.domain.home.dto;

import com.runningmate.server.domain.politicians.model.Politician;

public record PopularPoliticianResponse (
        Long politicianId,
        String name,
        String imageUrl
){
    public static PopularPoliticianResponse entityToDto(Politician politician) {
        return new PopularPoliticianResponse(politician.getId(), politician.getName(), politician.getImageUrl());
    }
}
