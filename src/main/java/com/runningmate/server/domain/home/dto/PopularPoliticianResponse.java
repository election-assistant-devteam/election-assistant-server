package com.runningmate.server.domain.home.dto;

public record PopularPoliticianResponse (
        Long politicianId,
        String name,
        String imageUrl
){
}
