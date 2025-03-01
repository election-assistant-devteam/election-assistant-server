package com.runningmate.server.domain.notification.service;

import com.runningmate.server.domain.notification.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> findAll();
    Optional<Notification> findOne(Long id);
}
