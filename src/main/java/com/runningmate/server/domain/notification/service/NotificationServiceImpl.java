package com.runningmate.server.domain.notification.service;

import com.runningmate.server.domain.notification.dto.NotificationItem;
import com.runningmate.server.domain.notification.dto.NotificationList;
import com.runningmate.server.domain.notification.model.Notification;
import com.runningmate.server.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;

    @Override
    public NotificationList findAll() {
        List<NotificationItem> notificationItems = notificationRepository.findAll()
                .stream()
                .map(NotificationItem::fromEntity)
                .collect(Collectors.toList());
        return new NotificationList(notificationItems);
    }

    @Override
    public Optional<NotificationItem> findOne(Long id) {
        return notificationRepository.findById(id)
                .map(NotificationItem::fromEntity);
    }
}
