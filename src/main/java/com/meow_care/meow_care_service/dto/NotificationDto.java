package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Notification;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Notification}
 */
public record NotificationDto(
        UUID id,
        UUID userId,
        @Size(max = 100) String title,
        String message,
        @Size(max = 50) String type,
        UUID relatedId,
        @Size(max = 50) String relatedType,
        Boolean isRead,
        @Size(max = 20) String status,
        Instant createdAt,
        Instant updatedAt
) {
}