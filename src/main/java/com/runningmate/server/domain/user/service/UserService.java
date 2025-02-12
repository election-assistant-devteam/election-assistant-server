package com.runningmate.server.domain.user.service;

import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public void createUser(String username, String password, String nickname, String email) {
        if(validateUsername(username)){
            throw new IllegalArgumentException("동일한 아이디를 가진 유저가 존재합니다");
        }
        if(validateEmail(email)){
            throw new IllegalArgumentException("동일한 이메일을 가진 유저가 존재합니다");
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
