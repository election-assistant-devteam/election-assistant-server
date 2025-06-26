package com.runningmate.server.domain.user.repository;

import com.runningmate.server.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("""
        UPDATE User u 
        SET u.password = :password
        WHERE u.id = :id
        """)
    int updatePassword(
            @Param("id")       Long id,
            @Param("password") String password
    );

    @Modifying
    @Transactional
    @Query("""
        UPDATE User u 
        SET u.nickname = :nickname
        WHERE u.id = :id
        """)
    int updateNickname(
            @Param("id")       Long id,
            @Param("nickname") String nickname
    );
}
