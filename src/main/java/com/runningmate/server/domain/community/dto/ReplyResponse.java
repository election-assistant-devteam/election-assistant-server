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
    public static ReplyResponse entityToDto(Comment reply, boolean hasLiked){
        return ReplyResponse.builder()
                .commentId(reply.getId())
                .createdAt(reply.getCreatedAt())
                .writer(reply.getIsAnonymous() ? "익명 " : reply.getWriter().getNickname())
                .content(reply.getContent())
                .likeCount(reply.getLikeCount())
                .hasLiked(hasLiked)
                .build();
    }
}
