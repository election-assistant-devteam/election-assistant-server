package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CommentResponse(
        Long commentId,
        LocalDateTime createdAt,
        String writer,
        String content,
        Long likeCount,
        Boolean hasLiked,
        List<ReplyResponse> replies
) {
    public static CommentResponse entityToDto(Comment comment, boolean hasLiked, List<ReplyResponse> replies){
        return CommentResponse.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .writer(comment.getIsAnonymous() ? "익명" : comment.getWriter().getNickname())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .hasLiked(hasLiked)
                .replies(replies)
                .build();
    }
}
