package com.runningmate.server.domain.watch.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWatchListId {
    private Long userId;
    private Long politicianId;

    // equals, hashCode는 반드시 오버라이딩 필요 (JPA 내부에서 비교 시 사용)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWatchListId)) return false;
        UserWatchListId that = (UserWatchListId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(politicianId, that.politicianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, politicianId);
    }
}
