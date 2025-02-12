package com.runningmate.server.domain.user.model;

import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Table(name = "users")
@Entity
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String username;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(length = 20, nullable = false)
    private String nickname;
    @Column(length = 320, nullable = false)
    private String email;
    @Column(length = 100)
    private String partyOfInterest;
    @Column(length = 100)
    private String politicianOfInterest;
}
