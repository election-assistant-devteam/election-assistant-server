package com.runningmate.server.domain.community.controller;

import com.runningmate.server.domain.community.dto.LikeCommentResponse;
import com.runningmate.server.domain.community.service.CommentService;
import com.runningmate.server.global.common.response.BaseResponse;
import com.runningmate.server.global.jwt.LoginUserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("comments")
@RestController
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{commentId}/likes")
    public BaseResponse<LikeCommentResponse> likeComment(@LoginUserId Long userId, @PathVariable Long commentId){
        log.info("[likeComment] userId = {}, commentId = {}", userId, commentId);
        return new BaseResponse<>(commentService.likeComment(userId, commentId));
    }
}
