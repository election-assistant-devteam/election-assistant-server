package com.runningmate.server.domain.calendar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetSchedulesResponse {
    private List<SchedulesForDate> calendar;
}
