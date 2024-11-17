package com.meow_care.meow_care_service.services.Impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.meow_care.meow_care_service.entities.Notification;
import com.meow_care.meow_care_service.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void saveNotification(UUID userId, String title, String message) {
        Firestore db = FirestoreClient.getFirestore();

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
        ApiFuture<WriteResult> result = db.collection("notify")
                .document(notificationId.toString())
                .set(notification);

        // Log result
        try {
            log.info("Notification written at: {}", result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
