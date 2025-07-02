package com.runningmate.server.domain.community.repository;

import com.runningmate.server.domain.community.model.Comment;
import com.runningmate.server.domain.community.model.CommentLike;
import com.runningmate.server.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
}
