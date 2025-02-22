package com.runningmate.server.domain.auth.contoller;

import com.runningmate.server.domain.auth.dto.LoginRequest;
import com.runningmate.server.domain.auth.dto.LoginResponse;
import com.runningmate.server.domain.auth.service.LoginService;
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
@RequestMapping("auth")
public class AuthController {
    private final LoginService loginService;
    @PostMapping("login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request){
        log.info("[login] username={} password={}", request.getUsername(), request.getPassword());
        return new BaseResponse<>(loginService.login(request));
    }
}
