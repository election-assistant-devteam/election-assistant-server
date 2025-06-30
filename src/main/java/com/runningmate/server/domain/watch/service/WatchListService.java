package com.runningmate.server.domain.watch.service;

import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.domain.watch.dto.AddWatchListResponse;
import com.runningmate.server.domain.watch.dto.WatchedPoliticianResponse;
import com.runningmate.server.domain.watch.model.UserWatchList;
import com.runningmate.server.domain.watch.model.UserWatchListId;
import com.runningmate.server.domain.watch.repository.WatchListRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final PoliticianRepository politicianRepository;

    public AddWatchListResponse followPolitician(Long userId, Long politicianId){

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new EntityNotFoundException(USER_NOT_FOUND)
                );
        Politician politician = politicianRepository.findById(politicianId)
                .orElseThrow(() -> new EntityNotFoundException(POLITICIAN_NOT_FOUND));

        UserWatchListId id = new UserWatchListId(userId, politicianId);

        if (!watchListRepository.existsById(id)) {
            watchListRepository.save(UserWatchList.builder()
                                    .id(id)
                                    .user(user)
                                    .politician(politician)
                                    .build());
        }

        return new AddWatchListResponse(politicianId);
    }


    public List<WatchedPoliticianResponse> getWatchedPoliticians(Long userId) {
        List<UserWatchList> watchList = watchListRepository.findByUser_Id(userId);

        return watchList.stream()
                .map(watch -> WatchedPoliticianResponse.from(watch.getPolitician()))
                .toList();
    }


}
