package com.runningmate.server.domain.notification.repository;

import com.runningmate.server.domain.notification.dto.NotificationList;
import com.runningmate.server.domain.notification.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class NotificationRepositoryTests {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Notification notification = Notification.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .build();
        Notification result = notificationRepository.save(notification);
        log.info("Id"+result.getId());
        });
    }
    @Test
    public void testSelect() {
        List<Notification> notificationList = notificationRepository.findAll();
        log.info("NotificationList"+notificationList.toString());
    }

}
