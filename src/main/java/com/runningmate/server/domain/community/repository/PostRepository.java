package com.runningmate.server.domain.community.repository;

import com.runningmate.server.domain.community.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
            select p from Post p
            where (:keyword is null or :keyword like '' or p.content like %:keyword% or p.title like %:keyword% )
            and (:lastId is null or p.id < :lastId)
            order by p.id desc
    """)
    List<Post> findWithKeywordAndCursor(@Param("keyword")String keyword, @Param("lastId") Long lastId, Pageable pageable);

    List<Post> findByLikeCountGreaterThanOrderByLikeCountDesc(int likeCount, Pageable pageable);

    long countByWriterId(Long writerId);
}
