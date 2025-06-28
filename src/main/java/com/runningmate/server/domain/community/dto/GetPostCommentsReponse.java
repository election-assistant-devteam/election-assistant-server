package com.runningmate.server.domain.community.dto;

import java.util.List;

public record GetPostCommentsReponse(
    List<CommentResponse> comments
) {
}
