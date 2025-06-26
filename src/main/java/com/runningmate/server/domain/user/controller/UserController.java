package com.runningmate.server.domain.user.controller;

import com.runningmate.server.domain.user.dto.CreateUserRequest;
import com.runningmate.server.domain.user.dto.UpdateUserRequest;
import com.runningmate.server.domain.user.service.UserService;
import com.runningmate.server.global.common.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/users/create")
    public BaseResponse<Void> createUser(@RequestBody @Valid CreateUserRequest request){
        log.info("[postUser] request = {}", request);
        userService.createUser(request.getUsername(), request.getPassword(), request.getNickname(), request.getEmail());
        return new BaseResponse<>(null);
    }

    @PostMapping("/users/update")
    public BaseResponse<Void> updateUser(@RequestParam("prevId") Long prevId, @RequestBody UpdateUserRequest req){
        log.info("[updateUser] prevId = {}", prevId);
        userService.updateUser(prevId, req.getPassword(), req.getNickname());
        return new BaseResponse<>(null);
    }
}
