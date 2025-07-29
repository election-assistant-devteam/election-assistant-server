package com.runningmate.server.domain.user.dto;

import java.util.List;

public record GetMyWatchingPoliticians(
        List<MyWatchingPolitician> watchingPoliticians,
        Long lastId,
        boolean hasMore
) {
}
