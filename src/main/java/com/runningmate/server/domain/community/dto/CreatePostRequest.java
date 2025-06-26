package com.runningmate.server.domain.community.dto;

public record CreatePostRequest(
    String title,
    String content
) {
}
