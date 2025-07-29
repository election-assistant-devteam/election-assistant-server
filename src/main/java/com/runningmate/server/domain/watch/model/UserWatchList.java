package com.runningmate.server.domain.watch.model;

import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserWatchList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "politician_id", nullable = false)
    private Politician politician;

    @Builder
    public UserWatchList(User user, Politician politician) {
        this.user = user;
        this.politician = politician;
    }

}
