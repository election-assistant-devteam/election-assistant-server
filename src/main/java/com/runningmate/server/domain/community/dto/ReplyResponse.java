package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReplyResponse(
        Long commentId,
        LocalDateTime createdAt,
        String writer,
        String content,
        Long likeCount,
        Boolean hasLiked
) {
    public static ReplyResponse entityToDto(Comment comment){
        return ReplyResponse.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .writer(comment.getIsAnonymous() ? "익명 " : comment.getWriter().getNickname())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .hasLiked(false)
                .build();
    }
}
