package com.runningmate.server.domain.watch.model;

import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserWatchList {
    @EmbeddedId
    private UserWatchListId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("politicianId")
    @JoinColumn(name = "politician_id", nullable = false)
    private Politician politician;

    @Builder
    public UserWatchList(User user, Politician politician) {
        this.id = new UserWatchListId(user.getId(), politician.getId());
        this.user = user;
        this.politician = politician;
    }

}
