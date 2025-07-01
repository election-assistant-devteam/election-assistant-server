package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.CommentResponse;
import com.runningmate.server.domain.community.dto.CreateCommentOnPostRequest;
import com.runningmate.server.domain.community.dto.GetPostCommentsReponse;
import com.runningmate.server.domain.community.dto.ReplyResponse;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.CommentLikeRepository;
import com.runningmate.server.domain.community.repository.CommentRepository;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public GetPostCommentsReponse findComments(Long userId, Long postId) {
        log.info("[findComments]");
        User user = null;

        // 로그인한 사용자인 경우 조회
        if(userId != null) {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        }

        // 게시물 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        // 댓글을 DTO로 변환
        List<Comment> parents = post.getComments().stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .filter(Comment::isParent).collect(Collectors.toList());

        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment : parents){
            List<ReplyResponse> replyResponses = new ArrayList<>();
            List<Comment> children = comment.getChildren().stream()
                    .sorted(Comparator.comparing(Comment::getCreatedAt))
                    .collect(Collectors.toList());
            for(Comment reply : children){
                ReplyResponse replyResponse = ReplyResponse.entityToDto(reply, hasUserLikedComment(user, reply));
                replyResponses.add(replyResponse);
            }
            CommentResponse commentResponse = CommentResponse.entityToDto(comment, hasUserLikedComment(user, comment), replyResponses);
            commentResponses.add(commentResponse);
        }

        return new GetPostCommentsReponse(commentResponses);
    }

    private boolean hasUserLikedComment(User user, Comment reply) {
        return user == null ? false : commentLikeRepository.existsByUserAndComment(user, reply);
    }

    public long createComment(Long userId, Long postId, CreateCommentOnPostRequest request) {
        log.info("[createComment]");

        // 유저를 찾는다
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        // 게시물을 찾는다
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        // 부모 댓글을 찾는다
        Comment parent = null;
        if (request.parentId() != null) {
            parent = commentRepository.findById(request.parentId()).orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
        }

        // 댓글을 추가한다
        Comment comment = Comment.builder()
                .content(request.content())
                .isAnonymous(request.isAnonymous())
                .parent(parent)
                .writer(user)
                .build();

        comment.setPost(post);

        Comment saved = commentRepository.save(comment);

        return saved.getId();
    }
}
