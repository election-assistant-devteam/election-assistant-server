package com.runningmate.server.domain.user.controller;

import com.runningmate.server.domain.user.dto.CreateUserRequest;
import com.runningmate.server.domain.user.service.UserService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public BaseResponse<Void> createUser(@RequestBody CreateUserRequest request){
        log.info("[postUser] request = {}", request);
        userService.createUser(request.getUsername(), request.getPassword(), request.getNickname(), request.getEmail());
        return new BaseResponse<>(null);
    }
}
