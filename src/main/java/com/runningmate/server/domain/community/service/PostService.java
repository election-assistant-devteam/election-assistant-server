package com.runningmate.server.domain.community.service;

import com.runningmate.server.domain.community.dto.CreatePostRequest;
import com.runningmate.server.domain.community.infrastructure.S3Uploader;
import com.runningmate.server.domain.community.model.Image;
import com.runningmate.server.domain.community.model.Post;
import com.runningmate.server.domain.community.repository.PostRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;
    public long create(long userId, List<MultipartFile> files, CreatePostRequest request) {
        log.info("[create]");

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

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
}
