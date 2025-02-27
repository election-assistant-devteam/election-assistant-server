package com.runningmate.server.domain.calendar.dto;

import com.runningmate.server.domain.calendar.model.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SchedulesForDate {
    private LocalDate date;
    List<ScheduleForDate> issues;

    public SchedulesForDate(LocalDate date, List<Schedule> schedulesPerDay) {
        this.date = date;
        this.issues = schedulesPerDay.stream().map(ScheduleForDate::fromSchedule).collect(Collectors.toList());
    }
}
