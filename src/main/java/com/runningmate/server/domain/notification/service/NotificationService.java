package com.runningmate.server.domain.notification.service;

import com.runningmate.server.domain.notification.dto.NotificationItem;
import com.runningmate.server.domain.notification.dto.NotificationList;

import java.util.Optional;

public interface NotificationService {

    NotificationList findAll();
    Optional<NotificationItem> findOne(Long id);
}
