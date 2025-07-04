package com.runningmate.server.domain.home.dto;

public record PopularPostResponse(
        String title,
        Long likeCount,
        Long commentCount
) {
}
