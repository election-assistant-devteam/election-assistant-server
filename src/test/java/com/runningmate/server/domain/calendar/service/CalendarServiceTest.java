package com.runningmate.server.domain.calendar.service;

import com.runningmate.server.domain.calendar.dto.GetSchedulesResponse;
import com.runningmate.server.domain.calendar.model.Schedule;
import com.runningmate.server.domain.calendar.model.ScheduleType;
import com.runningmate.server.domain.calendar.repository.ScheduleRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
//@Transactional
class CalendarServiceTest {
    @Autowired private UserRepository userRepository;
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private CalendarService calendarService;
    @Test
    @DisplayName("캘린더 일정 목록 조회 테스트")
    void findSchedulesTest(){
        // given
        final int YEAR = 2025;
        final int MONTH = 12;

        User user = User.builder()
                .username("user123")
                .nickname("장원영")
                .email("email@email.com")
                .password("password1234")
                .build();

        user = userRepository.save(user);

       for(int month = 1; month <= MONTH; month++){
            Schedule custom = Schedule.builder()
                    .date(LocalDate.of(YEAR, month, 5))
                    .type(ScheduleType.CUSTOM)
                    .name("개인일정")
                    .user(user)
                    .build();
            scheduleRepository.save(custom);

            Schedule election = Schedule.builder()
                    .date(LocalDate.of(YEAR, month, 5))
                    .type(ScheduleType.ELECTION)
                    .name("선거")
                    .electionId(1L)
                    .build();
            scheduleRepository.save(election);
        }
        Random random = new Random();

        // when
        GetSchedulesResponse schedules = calendarService.findSchedules(user.getId(), YEAR);

        // then
        Assertions.assertThat(schedules.getCalendar()).isNotNull();
        Assertions.assertThat(schedules.getCalendar()).hasSize(MONTH);
        Assertions.assertThat(schedules.getCalendar().get(random.nextInt(12)).getDate()).hasYear(YEAR);
    }
}