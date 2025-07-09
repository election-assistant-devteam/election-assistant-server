package com.runningmate.server.domain.user.dto;

import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.watch.dto.PoliticianWithWatchCount;

import java.util.List;
import java.util.stream.Collectors;

public record GetMyPageResponse(
        String nickname,
        String partyOfInterest,
        String politicianOfInterest,
        Long postCount,
        List<WatchingPoliticianResponse> watchingPoliticians
) {

    public static GetMyPageResponse from(User user, long postCount, List<PoliticianWithWatchCount> politiciansWithWatchCount){
        List<WatchingPoliticianResponse> watchingPoliticians = politiciansWithWatchCount.stream()
                .map(dto -> dto.politician())
                .map(WatchingPoliticianResponse::entityToDto)
                .collect(Collectors.toList());

        return new GetMyPageResponse(user.getNickname(), user.getPartyOfInterest(), user.getPoliticianOfInterest(), postCount, watchingPoliticians);
    }
}
