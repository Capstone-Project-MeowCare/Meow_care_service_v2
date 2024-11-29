package com.meow_care.meow_care_service.services.Impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.meow_care.meow_care_service.entities.Notification;
import com.meow_care.meow_care_service.repositories.NotificationRepository;
import com.meow_care.meow_care_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    @Override
    public void saveNotification(UUID userId, String title, String message) {
        // Create notification data
        UUID notificationId = UUID.randomUUID();
        Notification notification = Notification.builder()
                .id(notificationId.toString())
                .userId(userId.toString())
                .title(title)
                .message(message)
                .type("GENERAL")
                .build();

        // Add to Firestore
        ApiFuture<WriteResult> result = notificationRepository.saveNotification(notification);

        // Log result
        try {
            log.info("Notification written at: {}", result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
