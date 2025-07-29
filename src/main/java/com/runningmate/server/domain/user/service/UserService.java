package com.runningmate.server.domain.user.service;

import com.runningmate.server.domain.community.dto.PostSummaryDto;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.dto.GetMyPageResponse;
import com.runningmate.server.domain.user.dto.GetMyPostsResponse;
import com.runningmate.server.domain.user.dto.GetMyWatchingPoliticians;
import com.runningmate.server.domain.user.dto.MyWatchingPolitician;
import com.runningmate.server.domain.user.exception.SameUserExistsException;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.domain.watch.dto.PoliticianWithWatchCount;
import com.runningmate.server.domain.watch.repository.WatchListRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
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

    public GetMyPostsResponse findUserPostsByCursor(Long userId, Long lastId, int size) {
        log.info("[findUserPosts]");

        // 사용자가 작성한 게시글을 조회
        List<Post> posts = postRepository.findUserPostsByCursor(userId, lastId, PageRequest.of(0, size + 1));

        // dto로 변환
        List<PostSummaryDto> summarys = posts.stream().map(PostSummaryDto::entityToDto).limit(size).collect(Collectors.toList());
        Long nextLastId = summarys.size() > 0 ? summarys.get(summarys.size() - 1).postId() : 0L;
        Boolean hasMore = posts.size() > size;

        return new GetMyPostsResponse(summarys, nextLastId, hasMore);
    }

    public GetMyWatchingPoliticians findWatchingPoliticiansByCursor(Long userId, Long lastId, int size) {
        log.info("[findWatchingPoliticiansByCursor]");

        // 지켜보기 중인 정치인을 페이징해서 조회
        List<MyWatchingPolitician> politicians = watchListRepository.findWatchingPoliticiansByCursor(userId, lastId, PageRequest.of(0, size + 1));

        boolean hasMore = politicians.size() > size;
        if(hasMore){
            politicians = politicians.subList(0, size);
        }
        Long newLastId = politicians.size() > 0 ? politicians.get(politicians.size() - 1).watchingPoliticianId() : 0L;

        // dto로 응답
        return new GetMyWatchingPoliticians(politicians, newLastId, hasMore);
    }
}
