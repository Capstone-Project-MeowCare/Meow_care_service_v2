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

    private static final String COLLECTION_NAME = "notify";

    @Override
    public ApiFuture<WriteResult> saveNotification(Notification notification) {
        return db.collection(COLLECTION_NAME)
                .document(notification.getId())
                .set(notification);
    }
}
