package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Post;
import lombok.Builder;

@Builder
public record PostSummaryDto (
        Long postId,
        String title,
        String content,
        Long likeCount,
        Long commentCount
){
    public static PostSummaryDto entityToDto(Post entity){
        return PostSummaryDto.builder()
                .postId(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .likeCount(entity.getLikeCount())
                .commentCount(entity.getCommentCount())
                .build();
    }
}
