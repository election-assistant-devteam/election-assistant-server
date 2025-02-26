package com.runningmate.server.domain.calendar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SchedulesForDate {
    private LocalDate date;
    List<ScheduleForDate> issues;
}
