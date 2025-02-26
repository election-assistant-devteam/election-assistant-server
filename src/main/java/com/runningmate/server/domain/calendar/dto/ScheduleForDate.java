package com.runningmate.server.domain.calendar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleForDate {
    private Long id;
    private String name;
    private Boolean isCustom;
    private Boolean isElection;
    private Long electionId;
}
