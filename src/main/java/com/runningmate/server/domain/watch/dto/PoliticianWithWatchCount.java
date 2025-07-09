package com.runningmate.server.domain.watch.dto;

import com.runningmate.server.domain.politicians.model.Politician;

public record PoliticianWithWatchCount(
        Politician politician,
        Long count
) {
}
