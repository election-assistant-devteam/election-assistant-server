package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record GetPostCommentsReponse(
    List<CommentResponse> comments
) {
    public static GetPostCommentsReponse createFromEntityList(List<Comment> comments){
        return new GetPostCommentsReponse(comments.stream()
                .map(CommentResponse::entityToDto)
                .collect(Collectors.toList()));
    }
}
