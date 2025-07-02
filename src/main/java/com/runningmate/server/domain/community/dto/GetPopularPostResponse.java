package com.runningmate.server.domain.community.dto;

import java.util.List;

public record GetPopularPostResponse(
        List<PostSummaryDto> popularPosts
) {
}
