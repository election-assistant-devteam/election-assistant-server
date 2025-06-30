package com.runningmate.server.domain.community.dto;

import lombok.Builder;

@Builder
public record CreateCommentOnPostRequest(
        Boolean isAnonymous,
        String content,
        Long parentId
) {
}
