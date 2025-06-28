package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.*;
import com.runningmate.server.domain.community.infrastructure.S3Uploader;
import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.Image;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.CommentRepository;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;
    public long create(long userId, List<MultipartFile> files, CreatePostRequest request) {
        log.info("[create]");

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        // 이미지 리스트를 s3에 저장
        List<Image> images = uploadImagesToS3(files);

        // 게시물을 저장
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .writer(user)
                .build();

        images.stream().forEach(image -> image.setPost(post));

        Post save = postRepository.save(post);

        return save.getId();
    }

    private List<Image> uploadImagesToS3(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String imageUrl = null;
            try {
                imageUrl = s3Uploader.upload(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            images.add(Image.builder()
                    .imageUrl(imageUrl)
                    .imageOrder(i + 1)
                    .build());
        }
        return images;
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

    public GetPostsResponse findPageByCursor(Long lastId, String keyword, int pageSize) {
        log.info("[getPosts]");

        // 페이지크기만큼 조회한다
        List<Post> posts = postRepository.findWithKeywordAndCursor(keyword, lastId, PageRequest.of(0, pageSize + 1));


        // 다음 페이지 존재여부를 확인한다.
        boolean hasNext = posts.size() > pageSize ? true : false;

        // 리턴한다
        List<PostSummaryDto> summarys = posts.stream().map(entity -> PostSummaryDto.builder()
                            .postId(entity.getId())
                            .title(entity.getTitle())
                            .content(entity.getContent())
                            .likeCount(entity.getLikeCount())
                            .commentCount(entity.getCommentCount())
                            .build())
                    .limit(pageSize)
                    .collect(Collectors.toList());

        return new GetPostsResponse(summarys,
                summarys.get(summarys.size() - 1).postId(),
                hasNext);
    }

    public GetPostResponse findById(Long postId) {
        log.info("[findById]");

        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        return GetPostResponse.entityToDto(post);
    }
}
