package com.runningmate.server.domain.home.service;

import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.home.dto.GetHomeResponse;
import com.runningmate.server.domain.home.dto.PopularPoliticianResponse;
import com.runningmate.server.domain.home.dto.PopularPostResponse;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.watch.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeService {
    private final WatchListRepository watchListRepository;
    private final PoliticianRepository politicianRepository;
    private final PostRepository postRepository;
    public GetHomeResponse getData(final int politicianSize, final int postSize) {
        log.info("[getData]");

        if (politicianSize < 0 || postSize < 0) {
           throw new IllegalArgumentException("조회할 데이터의 수는 음수일 수 없습니다.");
        }

        // 인기 정치인 조회
        List<Politician> topPoliticians = watchListRepository.findTopPoliticians(PageRequest.of(0, politicianSize));
        if(topPoliticians.size() < politicianSize){ // 부족할 경우 랜덤하게 선택된 정치인을 추가
            addRandomPoliticians(politicianSize, topPoliticians);
        }
        List<PopularPoliticianResponse> popularPoliticians = topPoliticians.stream().map(PopularPoliticianResponse::entityToDto).collect(Collectors.toList());

        // 인기 게시글 조회
        List<Post> topPosts = postRepository.findByLikeCountGreaterThanOrderByLikeCountDesc(0, PageRequest.of(0, postSize));
        List<PopularPostResponse> popularPosts = topPosts.stream().map(PopularPostResponse::entityToDto).collect(Collectors.toList());

        return new GetHomeResponse(popularPoliticians, popularPosts);
    }

    private void addRandomPoliticians(int politicianSize, List<Politician> topPoliticians) {
        log.info("정치인 수가 충분하지 않아 {}명만 반환됨", topPoliticians.size());

        Set<Long> existingIds = topPoliticians.stream()
                .map(Politician::getId)
                .collect(Collectors.toSet());

        List<Politician> randomPoliticians = politicianRepository.findRandomPoliticians(politicianSize * 2);

        List<Politician> additionalData = randomPoliticians.stream()
                .filter(politician -> !existingIds.contains(politician.getId()))
                .limit(politicianSize - topPoliticians.size())
                .collect(Collectors.toList());

        topPoliticians.addAll(additionalData);
    }
}
