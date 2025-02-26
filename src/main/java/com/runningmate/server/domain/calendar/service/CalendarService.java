package com.runningmate.server.domain.calendar.service;

import com.runningmate.server.domain.calendar.dto.GetSchedulesResponse;
import com.runningmate.server.domain.calendar.dto.SchedulesForDate;
import com.runningmate.server.domain.calendar.model.Schedule;
import com.runningmate.server.domain.calendar.repository.ScheduleRepository;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.domain.user.repository.UserRepository;
import com.runningmate.server.global.common.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.USER_NOT_FOUND;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    public GetSchedulesResponse findSchedules(Long userId, Integer year) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        List<SchedulesForDate> dates = new ArrayList<>();
        IntStream.range(1, 13)
                .forEach(month -> {
                    LocalDate firstDay = LocalDate.of(year, month, 1);
                    LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
                    log.info("[findSchedules] firstDay={} lastDay={}", firstDay, lastDay);
                    List<Schedule> schedulesOfMonth = scheduleRepository.findByUserIdAndDateRange(user, firstDay, lastDay);
                    log.info("[findSchedules] schedulesOfMonth size={}", schedulesOfMonth.size() );
                    Map<LocalDate, List<Schedule>> schedulesMap = schedulesOfMonth.stream().collect(groupingBy(schedule -> schedule.getDate()));
                    for(LocalDate key : schedulesMap.keySet()){
                        List<Schedule> schedulesPerDay = schedulesMap.get(key);
                        dates.add(new SchedulesForDate(key, schedulesPerDay));
                    }
                });
        return new GetSchedulesResponse(dates);
    }
}
