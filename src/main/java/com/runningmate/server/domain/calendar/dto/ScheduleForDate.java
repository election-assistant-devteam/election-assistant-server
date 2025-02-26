package com.runningmate.server.domain.calendar.dto;

import com.runningmate.server.domain.calendar.model.Schedule;
import com.runningmate.server.domain.calendar.model.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleForDate {
    private Long id;
    private String name;
    private Boolean isCustom;
    private Boolean isElection;
    private Long electionId;

    public static ScheduleForDate fromSchedule(Schedule schedule){
        return ScheduleForDate.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .isCustom(schedule.getType() == ScheduleType.CUSTOM)
                .isElection(schedule.getType() == ScheduleType.ELECTION)
                .electionId(schedule.getType() == ScheduleType.ELECTION ? schedule.getElectionId() : null)
                .build();
    }
}
