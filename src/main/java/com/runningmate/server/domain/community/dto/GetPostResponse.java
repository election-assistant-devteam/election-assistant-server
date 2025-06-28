package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetPostResponse (
    Long postId,
    LocalDateTime createdAt,
    String writer,
    String title,
    String content,
    Long likeCount,
    Long commentCount,
    Boolean hasLiked
){
    public static GetPostResponse entityToDto(Post post){
        return GetPostResponse.builder()
                .postId(post.getId())
                .createdAt(post.getCreatedAt())
                .writer(post.getWriter().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .hasLiked(false)   // 아직 구현하기 전임
                .build();
    }
}
