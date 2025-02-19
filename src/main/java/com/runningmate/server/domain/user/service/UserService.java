package com.runningmate.server.domain.user.service;

import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.SAME_USERNAME_EXISTS;
import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.SAME_USER_EMAIL_EXISTS;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public void createUser(String username, String password, String nickname, String email) {
        if(validateUsername(username)){
            throw new SameUserExistsException(SAME_USERNAME_EXISTS);
        }
        if(validateEmail(email)){
            throw new SameUserExistsException(SAME_USER_EMAIL_EXISTS);
        }
        User newUser = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
        userRepository.save(newUser);
    }

    private boolean validateUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean validateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
