package com.runningmate.server.domain.calendar.repository;

import com.runningmate.server.domain.calendar.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
