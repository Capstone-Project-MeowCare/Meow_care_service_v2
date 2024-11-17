package com.meow_care.meow_care_service.services;

import java.util.UUID;

public interface NotificationService {
    void saveNotification(UUID userId, String title, String message);
}
