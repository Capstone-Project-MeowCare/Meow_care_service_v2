package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Message;
import com.meow_care.meow_care_service.enums.MessageType;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Message}
 */
public record MessageDto(UUID id,
                         String content,
                         MessageType contentType,
                         Boolean isRead,
                         Instant createdAt,
                         Instant updatedAt) {
}