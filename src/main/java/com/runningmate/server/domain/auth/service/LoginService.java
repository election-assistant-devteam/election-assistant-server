package com.runningmate.server.domain.auth.service;

import com.runningmate.server.domain.auth.dto.LoginRequest;
import com.runningmate.server.domain.auth.dto.LoginResponse;
import com.runningmate.server.domain.auth.exception.LoginFailedException;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.UserNotFoundException;
import com.runningmate.server.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.INCORRECT_PASSWORD;
import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.INCORRECT_USERNAME;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public LoginResponse login(LoginRequest request) {
        log.info("[login]");
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(()->new LoginFailedException(INCORRECT_USERNAME));

        if(!user.hasSamePassword(password)){
            throw new LoginFailedException(INCORRECT_PASSWORD);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return LoginResponse.from(accessToken, refreshToken, user);
    }
}
