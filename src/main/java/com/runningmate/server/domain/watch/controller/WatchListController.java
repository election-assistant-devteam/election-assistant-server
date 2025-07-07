package com.runningmate.server.domain.watch.controller;

import com.runningmate.server.domain.watch.dto.AddWatchListResponse;
import com.runningmate.server.domain.watch.dto.WatchedPoliticianResponse;
import com.runningmate.server.domain.watch.service.WatchListService;
import com.runningmate.server.global.common.response.BaseResponse;
import com.runningmate.server.global.jwt.LoginUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WatchListController {
    private final WatchListService watchListService;

    @PostMapping("/politicians/likes/{politicianId}")
    public BaseResponse<Object> addInterestPoliticians(@LoginUserId Long userId, @PathVariable Long politicianId){
        AddWatchListResponse addWatchListResponse = watchListService.followPolitician(userId, politicianId);
        return new BaseResponse<>(addWatchListResponse);
    }

    @GetMapping("/politicians/watch")
    public BaseResponse<List<WatchedPoliticianResponse>> getWatchList(@LoginUserId Long userId) {
        List<WatchedPoliticianResponse> response = watchListService.getWatchedPoliticians(userId);
        return new BaseResponse<>(response);
    }

    @GetMapping("/politicians/watch/tf/{politicianId}")
    public BaseResponse<Boolean> getWatchTF(@LoginUserId Long userId, @PathVariable Long politicianId) {
        Boolean response = watchListService.getWatchTF(userId, politicianId);
        return new BaseResponse<>(response);
    }
}
