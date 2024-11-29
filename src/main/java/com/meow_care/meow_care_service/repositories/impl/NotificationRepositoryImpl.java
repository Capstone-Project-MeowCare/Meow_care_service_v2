package com.meow_care.meow_care_service.repositories.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.meow_care.meow_care_service.entities.Notification;
import com.meow_care.meow_care_service.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final Firestore db;

    private final String collectionName = "notify";

    @Override
    public ApiFuture<WriteResult> saveNotification(Notification notification) {
        return db.collection(collectionName)
                .document(notification.getId())
                .set(notification);
    }
}
