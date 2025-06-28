package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Image;
import com.runningmate.server.domain.community.model.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record GetPostResponse (
    Long postId,
    LocalDateTime createdAt,
    String writer,
    String title,
    String content,
    Long likeCount,
    Long commentCount,
    Boolean hasLiked,
    List<String> images
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
                .images(post.getImages().stream()
                        .sorted(Comparator.comparing(Image::getImageOrder))
                        .map(Image::getImageUrl)
                        .collect(Collectors.toList()))
                .build();
    }
}
