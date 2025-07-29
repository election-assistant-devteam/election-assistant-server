package com.runningmate.server.domain.user.controller;

import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.news.schedule.NewsScheduler;
import com.runningmate.server.domain.news.service.NewsService;
import com.runningmate.server.domain.politicians.init.PoliticianInitializer;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.service.UserService;
import com.runningmate.server.domain.watch.dto.AddWatchListResponse;
import com.runningmate.server.domain.watch.service.WatchListService;
import com.runningmate.server.global.jwt.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsScheduler newsScheduler; // 테스트에서 제외하기 위함

    @MockBean
    private NewsService newsService;  // 테스트에서 제외하기 위함

    @MockBean
    private PoliticianInitializer initializer; // 테스트에서 제외하기 위함

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private PoliticianRepository politicianRepository;

    @Autowired
    private PostRepository postRepository;


    @Test
    void givenUserWithTwoWatchingPoliticians_getUsers_thenReturnTwoWatchingPoliticians() throws Exception {
        // given
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        IntStream.rangeClosed(1, 2).forEach(i -> {
            Politician politician = createPolitician("정치인" + i, "정정당당");
            watchListService.followPolitician(user.getId(), politician.getId());
        });

        entityManager.flush();
        entityManager.clear();

        // when
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        ResultActions actions = mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.watchingPoliticians.length()").value(2))
                .andExpect(jsonPath("$.data.postCount").value(0))
                .andExpect(jsonPath("$.data.partyOfInterest").isEmpty())
                .andExpect(jsonPath("$.data.politicianOfInterest").isEmpty());
    }

    private Politician createPolitician(String name, String party) {
        Politician build = Politician.builder()
                .name(name)
                .party(party)
                .build();

        return politicianRepository.save(build);
    }

    @Test
    void givenUserWithInterestingPartyAndPolitician_getUsers_thenReturnInterestingPartyAndPolitician() throws Exception {
        // given
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        final String PARTY = "성심당";
        final String POLITICIAN = "망고시루";

        userService.updatePreference(user.getId(), PARTY, POLITICIAN);

        entityManager.flush();
        entityManager.clear();

        // when
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        ResultActions actions = mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.watchingPoliticians.length()").value(0))
                .andExpect(jsonPath("$.data.postCount").value(0))
                .andExpect(jsonPath("$.data.partyOfInterest").value(PARTY))
                .andExpect(jsonPath("$.data.politicianOfInterest").value(POLITICIAN));
    }

    @Test
    void givenUserWroteTwoPosts_getUsers_thenPostCountIsTwo() throws Exception {
        // given
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        IntStream.rangeClosed(1, 2).forEach(i -> {
            createPostWithTitleAndContent(user, "제목", "내용");
        });

        entityManager.flush();
        entityManager.clear();

        // when
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        ResultActions actions = mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.watchingPoliticians.length()").value(0))
                .andExpect(jsonPath("$.data.postCount").value(2))
                .andExpect(jsonPath("$.data.partyOfInterest").isEmpty())
                .andExpect(jsonPath("$.data.politicianOfInterest").isEmpty());
    }

    private Post createPostWithTitleAndContent(User user, String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .writer(user)
                .build();

        postRepository.save(post);

        return post;
    }

    @Test
    void should_ReturnTwoMyWatcingPoliticians_When_UserMadeTwoWatchingPoliticians() throws Exception {
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        List<Long> watchingPoliticianIds = new ArrayList();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            Politician politician = createPolitician("정치인" + i, "정정당당");
            AddWatchListResponse response = watchListService.followPolitician(user.getId(), politician.getId());
            watchingPoliticianIds.add(response.getWatchingPoliticianId());
        });

        entityManager.flush();
        entityManager.clear();

        // when
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        ResultActions actions = mockMvc.perform(get("/users/watching-politicians")
                .header("Authorization", "Bearer " + token));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.watchingPoliticians.length()").value(2))
                .andExpect(jsonPath("$.data.lastId").value(watchingPoliticianIds.get(0)))
                .andExpect(jsonPath("$.data.hasMore").value(false));
    }

    @Test
    void should_ReturnHasMoreWithTrue_When_UserMadeWatchingPoliticiansOverSize() throws Exception {
        // given
        final int size = 20;
        final int exceedingAmount = 2;

        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");
        List<Long> watchingPoliticianIds = new ArrayList();
        IntStream.rangeClosed(1, size + exceedingAmount).forEach(i -> {
            Politician politician = createPolitician("정치인" + i, "정정당당");
            AddWatchListResponse response = watchListService.followPolitician(user.getId(), politician.getId());
            watchingPoliticianIds.add(response.getWatchingPoliticianId());
        });

        entityManager.flush();
        entityManager.clear();

        // when
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        ResultActions actions = mockMvc.perform(get("/users/watching-politicians")
                .header("Authorization", "Bearer " + token));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.watchingPoliticians.length()").value(size))
                .andExpect(jsonPath("$.data.lastId").value(watchingPoliticianIds.get(exceedingAmount)))
                .andExpect(jsonPath("$.data.hasMore").value(true));
    }
}