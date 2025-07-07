package com.runningmate.server.domain.user.service;

import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.dto.GetMyPageResponse;
import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.domain.watch.dto.PoliticianWithWatchCount;
import com.runningmate.server.domain.watch.repository.WatchListRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final WatchListRepository watchListRepository;

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

    public GetMyPageResponse findUserDataForMyPage(Long userId) {
        log.info("[findUserDataForMyPage] userId={}", userId);

        // 회원 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        // 게시글 개수 조회
        long postCount = postRepository.countByWriterId(userId);

        // 지켜보는 정치인 조회
        List<PoliticianWithWatchCount> politiciansWithWatchCount = watchListRepository.findTopPoliticiansByUserId(userId, PageRequest.of(0, 3));

        return GetMyPageResponse.from(user, postCount, politiciansWithWatchCount);
    }
}
