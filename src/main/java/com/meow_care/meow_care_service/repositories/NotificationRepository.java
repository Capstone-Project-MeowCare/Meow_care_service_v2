package com.meow_care.meow_care_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import com.meow_care.meow_care_service.entities.Notification;

public interface NotificationRepository {
    ApiFuture<WriteResult> saveNotification(Notification notification);
}
