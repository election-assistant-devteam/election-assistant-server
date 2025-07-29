package com.runningmate.server.domain.watch.repository;

import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.user.dto.MyWatchingPolitician;
import com.runningmate.server.domain.watch.dto.PoliticianWithWatchCount;
import com.runningmate.server.domain.watch.model.UserWatchList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<UserWatchList, Long> {

    // 특정 정치인을 특정 유저가 지켜보는지 여부
    boolean existsByUserIdAndPoliticianId(Long userId, Long politicianID);
    List<UserWatchList> findByUser_Id(Long userId);

    @Query("""
        select w.politician
        from UserWatchList w
        group by w.politician
        order by count(w.politician) desc
    """)
    List<Politician> findTopPoliticians(Pageable pageable);

    @Query("""
        select new com.runningmate.server.domain.watch.dto.PoliticianWithWatchCount(w.politician, count(w))
        from UserWatchList w
        where w.user.id = :userId
        group by w.politician
        order by count(w) desc
    """)
    List<PoliticianWithWatchCount> findTopPoliticiansByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
        select new com.runningmate.server.domain.user.dto.MyWatchingPolitician(uwl.id, p.id, p.party, p.name)
        from UserWatchList uwl join uwl.politician p 
        where uwl.user.id = :userId and (uwl.id < :lastId or :lastId is null)
        order by uwl.id desc
    """)
    List<MyWatchingPolitician> findWatchingPoliticiansByCursor(Long userId, Long lastId, Pageable pageable);

    void deleteByUserIdAndPoliticianId(Long userId, Long politicianId);
}
