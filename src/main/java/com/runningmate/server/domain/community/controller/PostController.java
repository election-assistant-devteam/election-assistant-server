package com.runningmate.server.domain.community.controller;

import com.runningmate.server.domain.community.dto.CreateCommentOnPostRequest;
import com.runningmate.server.domain.community.dto.CreatePostRequest;
import com.runningmate.server.domain.community.dto.GetPostResponse;
import com.runningmate.server.domain.community.dto.GetPostsResponse;
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
    @PostMapping
    public BaseResponse<Object> createPost(@LoginUserId Long userId, @RequestPart List<MultipartFile> images, @RequestPart CreatePostRequest request){
        log.info("[createPost] userId = {}", userId);
        long postId = postService.create(userId, images, request);
        return new BaseResponse(Map.of("postId", postId));
    }

    @PostMapping("{postId}/comments")
    public BaseResponse<Object> createCommentOnPost(@LoginUserId Long userId, @PathVariable Long postId, @RequestBody CreateCommentOnPostRequest request){
        log.info("[createCommentOnPost] userId={}", userId);
        long commmentId = postService.createComment(userId, postId, request);
        return new BaseResponse<>(Map.of("commentId", commmentId));
    }

    @GetMapping
    public BaseResponse<GetPostsResponse> getPosts(@LoginUserId Long userId, @RequestParam(required = false) Long lastId, @RequestParam(required = false) String keyword){
        log.info("[findPosts] userId = {} lastId = {} keyword = {}", userId, lastId, keyword);
        return new BaseResponse<>(postService.findPageByCursor(lastId, keyword, 10));
    }

    @GetMapping("/{postId}")
    public BaseResponse<GetPostResponse> getPost(@PathVariable Long postId){
        log.info("[getPost] postId = {}",  postId);
        return new BaseResponse<>(postService.findById(postId));
    }
}

