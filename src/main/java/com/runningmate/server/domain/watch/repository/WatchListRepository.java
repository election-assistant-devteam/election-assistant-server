package com.runningmate.server.domain.watch.repository;

import com.runningmate.server.domain.watch.model.UserWatchList;
import com.runningmate.server.domain.watch.model.UserWatchListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<UserWatchList, UserWatchListId> {

    // 특정 정치인을 특정 유저가 지켜보는지 여부
    boolean existsById(UserWatchListId id);
    List<UserWatchList> findByUser_Id(Long userId);
}
