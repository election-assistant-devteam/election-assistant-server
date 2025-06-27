package com.runningmate.server.domain.community.dto;

import lombok.Builder;

@Builder
public record PostSummaryDto (
        Long postId,
        String title,
        String content,
        Long likeCount,
        Long commentCount
){
}
