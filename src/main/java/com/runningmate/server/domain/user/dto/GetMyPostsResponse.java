package com.runningmate.server.domain.user.dto;

import com.runningmate.server.domain.community.dto.PostSummaryDto;

import java.util.List;

public record GetMyPostsResponse(
        List<PostSummaryDto> posts,
        Long lastId,
        Boolean hasMore
) {
}
