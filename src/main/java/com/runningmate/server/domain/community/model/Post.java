package com.runningmate.server.domain.community.model;

import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.global.common.exception.BadRequestException;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.LIKE_NOT_ALLOWED_TO_WRITER;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE post SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, length=256)
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private Long likeCount = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Long commentCount = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public PostLike addLike(User user) {
        if(user == writer){
            throw new BadRequestException(LIKE_NOT_ALLOWED_TO_WRITER);
        }

        this.likeCount++;
        return PostLike.builder()
                .post(this)
                .user(user)
                .build();
    }
}
