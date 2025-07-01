package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.exception.AlreadyLikedException;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.repository.CommentLikeRepository;
import com.runningmate.server.domain.community.repository.CommentRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.ALREADY_LIKED_COMMENT;
import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.LIKE_NOT_ALLOWED_TO_WRITER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;

    @Test
    void givenCommentAlreadyLiked_whenLikingComment_thenThrowAlreadyLikedException(){
        // given
        long userId = 1L;
        long commentId = 1L;

        User user = createUser(userId);
        Comment comment = createComment(commentId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.existsByUserAndComment(user, comment)).thenReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> commentService.likeComment(userId, commentId));

        // then
        assertThat(throwable)
                .isInstanceOf(AlreadyLikedException.class)
                .hasMessageContaining(ALREADY_LIKED_COMMENT.getMessage());
    }

    @Test
    void givenUserWroteComment_whenLikingComment_thenThrowBadRequestException(){
        // given
        long userId = 1L;
        long commentId = 1L;

        User user = createUser(userId);
        Comment comment = createComment(commentId, user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.existsByUserAndComment(user, comment)).thenReturn(false);

        // when
        Throwable throwable = catchThrowable(() -> commentService.likeComment(userId, commentId));

        // then
        assertThat(throwable)
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(LIKE_NOT_ALLOWED_TO_WRITER.getMessage());
    }

    private Comment createComment(long commentId, User user) {
        return Comment.builder()
                .id(commentId)
                .writer(user)
                .build();
    }

    private Comment createComment(long commentId) {
        return Comment.builder()
                .id(commentId)
                .build();
    }

    private User createUser(long userId) {
        return User.builder()
                .id(userId)
                .build();
    }
}