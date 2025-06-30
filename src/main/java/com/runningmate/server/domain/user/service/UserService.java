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

    public User createUser(String username, String password, String nickname, String email) {
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
        return userRepository.save(newUser);
    }

    public void updateUser(Long prevId, String password, String nickname) {
        User user = userRepository.findById(prevId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + prevId));

        if (password != null) {
            userRepository.updatePassword(prevId, password);
        }

        if (nickname != null) {
            userRepository.updateNickname(prevId, nickname);
        }
    }

    public void updatePreference(Long prevId, String party, String politician) {
        User user = userRepository.findById(prevId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + prevId));

        if (party != null) {
            userRepository.updateParty(prevId, party);
        }

        if (politician != null) {
            userRepository.updatePolitician(prevId, politician);
        }
    }

    private boolean validateUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean validateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
