package com.runningmate.server.domain.calendar.repository;

import com.runningmate.server.domain.calendar.model.Schedule;
import com.runningmate.server.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE (s.user IS NULL OR s.user = :user) AND s.date BETWEEN :start AND :end ORDER BY s.date ASC")
    List<Schedule> findByUserIdAndDateRange(
            @Param("user") User user,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    Optional<Schedule> findByElectionId(Long id);
}
