package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.*;
import com.runningmate.server.domain.community.exception.AlreadyLikedException;
import com.runningmate.server.domain.community.infrastructure.S3Uploader;
import com.runningmate.server.domain.community.model.Image;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.model.PostLike;
import com.runningmate.server.domain.community.repository.PostLikeRepository;
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
    private final S3Uploader s3Uploader;
    private final PostLikeRepository postLikeRepository;
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

    public GetPostsResponse findPageByCursor(Long lastId, String keyword, int pageSize) {
        log.info("[getPosts]");

        // 페이지크기만큼 조회한다
        List<Post> posts = postRepository.findWithKeywordAndCursor(keyword, lastId, PageRequest.of(0, pageSize + 1));


        // 다음 페이지 존재여부를 확인한다.
        boolean hasNext = posts.size() > pageSize ? true : false;

        // 리턴한다
        List<PostSummaryDto> summarys = posts.stream().map(PostSummaryDto::entityToDto)
                    .limit(pageSize)
                    .collect(Collectors.toList());

        return new GetPostsResponse(summarys,
                summarys.size() > 0 ? summarys.get(summarys.size() - 1).postId() : 0,
                hasNext);
    }

    public GetPostResponse findPost(Long userId, Long postId) {
        log.info("[findById]");

        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        boolean hasLiked = false;

        if(userId != null){
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
            hasLiked = postLikeRepository.existsByUserAndPost(user, post);
        }

        return GetPostResponse.entityToDto(post, hasLiked);
    }

    public LikePostResponse likePost(Long userId, Long postId) {
        log.info("[likePost]");

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        if(postLikeRepository.existsByUserAndPost(user, post)){
            throw new AlreadyLikedException(ALREADY_LIKED_POST);
        }

        PostLike postLike = post.addLike(user);

        postLikeRepository.save(postLike);

        return new LikePostResponse(post.getLikeCount());
    }

    public GetPopularPostResponse getPopularPosts() {
        List<Post> top5 = getTop5PopularPosts();
        List<PostSummaryDto> summarys = top5.stream().map(PostSummaryDto::entityToDto).collect(Collectors.toList());
        return new GetPopularPostResponse(summarys);
    }

    private List<Post> getTop5PopularPosts() {
        return postRepository.findByLikeCountGreaterThanOrderByLikeCountDesc(0, PageRequest.of(0, 5));
    }
}
