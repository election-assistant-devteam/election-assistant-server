package com.runningmate.server.domain.notification.service;

import com.runningmate.server.domain.notification.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
}
