package com.runningmate.server.domain.calendar.controller;

import com.runningmate.server.domain.calendar.dto.GetSchedulesResponse;
import com.runningmate.server.domain.calendar.service.CalendarService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("schedules")
    public BaseResponse<GetSchedulesResponse> getSchedules(@RequestParam("userId") Long userId, @RequestParam("year") Integer year){
        log.info("[getSchedules] userId={} year={}", userId, year);
        return new BaseResponse<>(calendarService.findSchedules(userId, year));
    }
}
