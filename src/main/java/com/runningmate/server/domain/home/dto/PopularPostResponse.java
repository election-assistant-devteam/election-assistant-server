package com.runningmate.server.domain.home.dto;

import com.runningmate.server.domain.community.model.Post;

public record PopularPostResponse(
        String title,
        Long likeCount,
        Long commentCount
) {
    public static PopularPostResponse entityToDto(Post entity) {
        return new PopularPostResponse(entity.getTitle(), entity.getLikeCount(), entity.getCommentCount());
    }
}
