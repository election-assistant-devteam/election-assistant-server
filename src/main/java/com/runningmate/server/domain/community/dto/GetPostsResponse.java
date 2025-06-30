package com.runningmate.server.domain.community.dto;

import java.util.List;

public record GetPostsResponse(
        List<PostSummaryDto> posts,
        Long lastId,
        Boolean hasMore
) {
}
