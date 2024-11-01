package com.mewcare.meow_care_service.dto;

import com.mewcare.meow_care_service.enums.MessageType;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Message}
 */
public record MessageDto(UUID id,
                         String content,
                         MessageType contentType,
                         Boolean isRead,
                         Instant createdAt,
                         Instant updatedAt) {
}