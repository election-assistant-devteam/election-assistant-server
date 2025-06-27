package com.runningmate.server.domain.community.dto;

public record CreateCommentOnPostRequest(
        Boolean isAnonymous,
        String content,
        Long parentId
) {
}
