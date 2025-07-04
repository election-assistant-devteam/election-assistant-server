package com.runningmate.server.domain.home.dto;

import java.util.List;

public record GetHomeResponse(
        List<PopularPoliticianResponse> popularPoliticians,
        List<PopularPostResponse> popularPostResponses
) {
}
