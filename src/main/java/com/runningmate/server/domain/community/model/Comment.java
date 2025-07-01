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

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.WRITER_NOT_ALLOWED_TO_ADD_LIKE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comment SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(nullable = false, length = 256)
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private Long likeCount = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isAnonymous = true;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    public void setPost(Post post){
        this.post = post;
        this.post.increaseCommentCount();
        post.getComments().add(this);
    }

    public boolean isParent(){
        return this.parent == null;
    }

    public CommentLike addLike(User user) {
        if(this.writer == user){
            throw new BadRequestException(WRITER_NOT_ALLOWED_TO_ADD_LIKE);
        }

        this.likeCount++;

        return CommentLike.builder()
                .comment(this)
                .user(user)
                .build();
    }
}
