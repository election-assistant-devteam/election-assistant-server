package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.LikeCommentResponse;
import com.runningmate.server.domain.community.exception.AlreadyLikedException;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.CommentLike;
import com.runningmate.server.domain.community.repository.CommentLikeRepository;
import com.runningmate.server.domain.community.repository.CommentRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    public LikeCommentResponse likeComment(Long userId, Long commentId) {
        log.info("[likeComment]");

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));

        // 이미 공감한 댓글은 다시 공감할 수 없음
        if(commentLikeRepository.existsByUserAndComment(user, comment)){
            throw new AlreadyLikedException(ALREADY_LIKED_COMMENT);
        }

        CommentLike commentLike = comment.addLike(user);

        commentLikeRepository.save(commentLike);

        return new LikeCommentResponse(comment.getLikeCount());
    }
}
