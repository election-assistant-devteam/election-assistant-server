package com.runningmate.server.domain.notification.controller;


import com.runningmate.server.domain.notification.dto.NotificationList;
import com.runningmate.server.domain.notification.model.Notification;
import com.runningmate.server.domain.notification.service.NotificationService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public BaseResponse<NotificationList> getNotification() {
        List<Notification> notificationList = notificationService.findAll();
        return new BaseResponse<>(new NotificationList(notificationList));
    }

}
