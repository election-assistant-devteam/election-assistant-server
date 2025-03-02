package com.runningmate.server.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.runningmate.server.domain.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationItem {
    private Long id;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    public static NotificationItem fromEntity(Notification notification) {
        return new NotificationItem(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getUpdatedAt()
        );
    }
}
