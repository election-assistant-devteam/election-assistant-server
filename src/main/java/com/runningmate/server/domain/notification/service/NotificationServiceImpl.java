package com.runningmate.server.domain.notification.service;

import com.runningmate.server.domain.notification.model.Notification;
import com.runningmate.server.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
    @Override
    public Optional<Notification> findOne(Long id) {
        return notificationRepository.findById(id);
    }
}
