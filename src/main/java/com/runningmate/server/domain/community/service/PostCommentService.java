package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.GetPostCommentsReponse;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.POST_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCommentService {
    private final PostRepository postRepository;
    public GetPostCommentsReponse findByPostId(Long postId) {
        // 게시물 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        // 댓글을 DTO로 변환
        List<Comment> comments = post.getComments();

        List<Comment> parents = comments.stream().filter(Comment::isParent).collect(Collectors.toList());

        return GetPostCommentsReponse.createFromEntityList(parents);
    }
}
