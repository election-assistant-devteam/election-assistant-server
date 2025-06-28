package com.runningmate.server.domain.community.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long commentId,
        LocalDateTime createdAt,
        String writer,
        String content,
        Long likeCount,
        Boolean hasLiked,
        List<ReplyResponse> replies
) {
}
