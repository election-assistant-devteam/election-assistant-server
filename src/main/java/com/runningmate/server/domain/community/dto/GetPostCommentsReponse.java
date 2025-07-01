package com.runningmate.server.domain.community.dto;

import com.runningmate.server.domain.community.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record GetPostCommentsReponse(
    List<CommentResponse> comments
) {
}
