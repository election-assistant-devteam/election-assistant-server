package com.runningmate.server.domain.community.dto;

import java.time.LocalDateTime;

public record ReplyResponse(
        Long commentId,
        LocalDateTime createdAt,
        String writer,
        String content,
        Long likeCount,
        Boolean hasLiked
) {
}
