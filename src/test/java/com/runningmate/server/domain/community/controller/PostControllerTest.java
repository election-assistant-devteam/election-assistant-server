package com.runningmate.server.domain.community.controller;

import com.runningmate.server.domain.community.dto.CreateCommentOnPostRequest;
import com.runningmate.server.domain.community.model.Image;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.community.service.CommentService;
import com.runningmate.server.domain.community.service.PostCommentService;
import com.runningmate.server.domain.community.service.PostService;
import com.runningmate.server.domain.news.schedule.NewsScheduler;
import com.runningmate.server.domain.politicians.init.PoliticianInitializer;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.service.UserService;
import com.runningmate.server.global.jwt.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsScheduler newsScheduler; // 테스트에서 제외하기 위함

    @MockBean
    private PoliticianInitializer initializer; // 테스트에서 제외하기 위함

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void givenTwoImages_whenGetPost_thenReturnTwoImages() throws Exception {
        // given
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        Post post = createPostWithImages(user, 2);

        entityManager.flush();
        entityManager.clear();

        // when
        // then
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        mockMvc.perform(get("/posts/" + post.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.images.length()").value(2));
    }

    @Test
    void givenPostsMoreThanPageSize_whenGetPosts_thenNextPageExists() throws Exception {
        // given
        User user = userService.createUser("user1", "pass", "작성자", "asdf@asdf");

        // 페이지 사이즈가 10개로 고정되어 있으니 1개 더 저장
        IntStream.rangeClosed(1, 11).forEach(i ->{
           createPostWithImages(user, 2);
        });

        entityManager.flush();
        entityManager.clear();

        // when
        // then
        String token = jwtUtil.generateAccessToken(user.getId(), user.getUsername());

        mockMvc.perform(get("/posts")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.posts.length()").value(10))
                .andExpect(jsonPath("$.data.hasMore").value(true));
    }

    @Test
    void givenTwoReplies_whenGetPostComments_thenReturnTwoReplies() throws Exception {
        // given
        User postWriter = userService.createUser("user1", "pass", "게시글작성자", "asdf@asdf");

        User commentWriter = userService.createUser("user2", "pass", "댓글작성자", "asdf2@asdf");

        Post post = createPostWithImages(postWriter, 2);

        List<Long> commentIdList = LongStream.rangeClosed(1, 2).mapToObj(i -> {
            Long commentId = postCommentService.createComment(commentWriter.getId(), post.getId(), CreateCommentOnPostRequest.builder()
                    .content("댓글" + i)
                    .parentId(null)
                    .isAnonymous(true)
                    .build());

            return commentId;
        }).collect(Collectors.toList());

        LongStream.rangeClosed(1, 2).forEach(i -> {
           postCommentService.createComment(commentWriter.getId(), post.getId(), CreateCommentOnPostRequest.builder()
                .content("대댓글" + i)
                .parentId(commentIdList.get(0))
                .isAnonymous(true)
                .build());
        });

        entityManager.flush();
        entityManager.clear();

        // when
        // then
        String token = jwtUtil.generateAccessToken(postWriter.getId(), postWriter.getUsername());

        mockMvc.perform(get("/posts/" + post.getId() + "/comments")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.comments.length()").value(2))
                .andExpect(jsonPath("$.data.comments[0].replies.length()").value(2));
    }

    @Test
    void givenAddLikeToPost_whenGetPost_ThenHasLikedIsTrue() throws Exception {
        // given
        User postWriter = userService.createUser("user1", "pass", "게시글작성자", "asdf@asdf");

        User likeAdder = userService.createUser("user2", "pass", "공감표시자", "asdf2@asdf");

        Post post = createPostWithImages(postWriter, 2);

        postService.likePost(likeAdder.getId(), post.getId());

        // when
        // then
        String token = jwtUtil.generateAccessToken(likeAdder.getId(), likeAdder.getUsername());

        mockMvc.perform(get("/posts/" + post.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hasLiked").value(true));
    }

    @Test
    void givenNotAddLikeToPost_whenGetPost_ThenHasLikedIsFalse() throws Exception {
        // given
        User postWriter = userService.createUser("user1", "pass", "게시글작성자", "asdf@asdf");

        User anotherUser = userService.createUser("user2", "pass", "공감표시자", "asdf2@asdf");

        Post post = createPostWithImages(postWriter, 2);

        // when
        // then
        String token = jwtUtil.generateAccessToken(anotherUser.getId(), anotherUser.getUsername());

        mockMvc.perform(get("/posts/" + post.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hasLiked").value(false));
    }

    @Test
    void givenAddLikeToComment_whenGetPostComments_thenHasLikedExists() throws Exception {
        // given
        User postWriter = userService.createUser("user1", "pass", "게시글작성자", "asdf@asdf");

        User commentWriter = userService.createUser("user2", "pass", "댓글작성자", "asdf2@asdf");

        Post post = createPostWithImages(postWriter, 2);

        List<Long> commentIdList = LongStream.rangeClosed(1, 2).map(i -> postCommentService.createComment(commentWriter.getId(), post.getId(), CreateCommentOnPostRequest.builder()
                .content("댓글").isAnonymous(false).parentId(null).build())).boxed().collect(toList());

        commentService.likeComment(postWriter.getId(), commentIdList.get(0));

        // when
        // then
        String token = jwtUtil.generateAccessToken(postWriter.getId(), postWriter.getUsername());

        mockMvc.perform(get("/posts/" + post.getId() + "/comments")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.comments[0].likeCount").value(1))
                .andExpect(jsonPath("$.data.comments[0].hasLiked").value(true))
                .andExpect(jsonPath("$.data.comments[1].likeCount").value(0))
                .andExpect(jsonPath("$.data.comments[1].hasLiked").value(false));
    }

    private Post createPostWithImages(User user, int imageCount) {
        List<Image> images = IntStream.rangeClosed(1, imageCount).mapToObj(
                i -> Image.builder()
                        .imageUrl("image-url" + i)
                        .imageOrder(i)
                        .build()
        ).collect(toList());


        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .writer(user)
                .build();

        images.forEach(image ->{
            image.setPost(post);
        });

        postRepository.save(post);
        return post;
    }
}