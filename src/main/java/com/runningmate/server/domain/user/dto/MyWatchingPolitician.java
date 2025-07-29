package com.runningmate.server.domain.user.dto;

public record MyWatchingPolitician(
        Long watchingPoliticianId,
        Long politicianId,
        String party,
        String name
){}