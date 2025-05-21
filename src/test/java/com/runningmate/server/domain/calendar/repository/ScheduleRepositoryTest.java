package com.runningmate.server.domain.calendar.repository;

import com.runningmate.server.domain.calendar.model.Schedule;
import com.runningmate.server.domain.calendar.model.ScheduleType;
import com.runningmate.server.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ScheduleRepositoryTest {
    @Autowired private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("저장하기 테스트")
    void saveTest(){
        Schedule schedule = Schedule.builder()
                .date(LocalDate.now())
                .name("선거일")
                .type(ScheduleType.OFFICIAL_EVENT)
                .build();

        schedule = scheduleRepository.save(schedule);

        assertThat(schedule.getType()).isEqualTo(ScheduleType.OFFICIAL_EVENT);
    }
}