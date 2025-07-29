package com.runningmate.server.domain.user.controller;

import com.runningmate.server.domain.user.dto.*;
import com.runningmate.server.domain.user.service.UserService;
import com.runningmate.server.global.common.response.BaseResponse;
import com.runningmate.server.global.jwt.LoginUserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public BaseResponse<Void> createUser(@RequestBody @Valid CreateUserRequest request){
        log.info("[postUser] request = {}", request);
        userService.createUser(request.getUsername(), request.getPassword(), request.getNickname(), request.getEmail());
        return new BaseResponse<>(null);
    }

    @PostMapping("/update")
    public BaseResponse<Void> updateUser(@RequestParam("prevId") Long prevId, @RequestBody UpdateUserRequest req){
        log.info("[updateUser] prevId = {}", prevId);
        userService.updateUser(prevId, req.getPassword(), req.getNickname());
        return new BaseResponse<>(null);
    }

    @PostMapping("/edit/preference")
    public BaseResponse<Void> updatePreference(@RequestParam("prevId") Long prevId, @RequestBody UpdateUserRequest req){
        log.info(req.getPartyOfInterest());
        userService.updatePreference(prevId, req.getPartyOfInterest(), req.getPoliticianOfInterest());
        return new BaseResponse<>(null);
    }

    @GetMapping
    public BaseResponse<GetMyPageResponse> getMyPage(@LoginUserId Long userId){
        log.info("[getMyPage] userId = {}", userId);
        return new BaseResponse<>(userService.findUserDataForMyPage(userId));
    }

    @GetMapping("/posts")
    public BaseResponse<GetMyPostsResponse> getMyPosts(@LoginUserId Long userId, @RequestParam(required = false) Long lastId){
        log.info("[getMyPosts] userId = {} lastId = {}", userId, lastId);
        return new BaseResponse<>(userService.findUserPostsByCursor(userId, lastId, 20));
    }

    @GetMapping("/watching-politicians")
    public BaseResponse<GetMyWatchingPoliticians> getMyWatchingPoliticians(@LoginUserId Long userId, @RequestParam(required = false) Long lastId){
        log.info("[getMyWatchingPoliticians] userId={} lastId={}", userId, lastId);
        return new BaseResponse<>(userService.findWatchingPoliticiansByCursor(userId, lastId));
    }
}
