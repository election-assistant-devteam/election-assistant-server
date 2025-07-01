package com.runningmate.server.domain.community.controller;

import com.runningmate.server.domain.community.dto.*;
import com.runningmate.server.domain.community.service.PostCommentService;
import com.runningmate.server.domain.community.service.PostService;
import com.runningmate.server.global.common.response.BaseResponse;
import com.runningmate.server.global.jwt.LoginUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("posts")
@RestController
public class PostController {
    private final PostService postService;
    private final PostCommentService postCommentService;
    @PostMapping
    public BaseResponse<Object> createPost(@LoginUserId Long userId, @RequestPart List<MultipartFile> images, @RequestPart CreatePostRequest request){
        log.info("[createPost] userId = {}", userId);
        long postId = postService.create(userId, images, request);
        return new BaseResponse(Map.of("postId", postId));
    }

    @PostMapping("{postId}/comments")
    public BaseResponse<Object> createCommentOnPost(@LoginUserId Long userId, @PathVariable Long postId, @RequestBody CreateCommentOnPostRequest request){
        log.info("[createCommentOnPost] userId={}", userId);
        long commmentId = postCommentService.createComment(userId, postId, request);
        return new BaseResponse<>(Map.of("commentId", commmentId));
    }

    @GetMapping
    public BaseResponse<GetPostsResponse> getPosts(@LoginUserId Long userId, @RequestParam(required = false) Long lastId, @RequestParam(required = false) String keyword){
        log.info("[findPosts] userId = {} lastId = {} keyword = {}", userId, lastId, keyword);
        return new BaseResponse<>(postService.findPageByCursor(lastId, keyword, 10));
    }

    @GetMapping("/{postId}")
    public BaseResponse<GetPostResponse> getPost(@LoginUserId Long userId, @PathVariable Long postId){
        log.info("[getPost] postId = {}",  postId);
        return new BaseResponse<>(postService.findPost(userId, postId));
    }

    @GetMapping("/{postId}/comments")
    public BaseResponse<GetPostCommentsReponse> getPostComments(@LoginUserId Long userId, @PathVariable Long postId){
        log.info("[getPostComments] userId = {}, postId = {}", userId, postId);
        return new BaseResponse<>(postCommentService.findComments(userId, postId));
    }

    @PostMapping("/{postId}/likes")
    public BaseResponse<Void> likePost(@LoginUserId Long userId, @PathVariable Long postId){
        log.info("[likePost] userId = {} postId = {}", userId, postId);
        postService.likePost(userId, postId);
        return new BaseResponse<>(null);
    }
}

