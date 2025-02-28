package com.runningmate.server.domain.notification.dto;

import com.runningmate.server.domain.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationList {
    private List<Notification> notificationList;
}
