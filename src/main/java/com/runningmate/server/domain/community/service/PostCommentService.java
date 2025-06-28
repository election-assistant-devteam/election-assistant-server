package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.CreateCommentOnPostRequest;
import com.runningmate.server.domain.community.dto.GetPostCommentsReponse;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.CommentRepository;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public GetPostCommentsReponse findByPostId(Long postId) {
        // 게시물 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        // 댓글을 DTO로 변환
        List<Comment> comments = post.getComments();

        List<Comment> parents = comments.stream().filter(Comment::isParent).collect(Collectors.toList());

        return GetPostCommentsReponse.createFromEntityList(parents);
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
