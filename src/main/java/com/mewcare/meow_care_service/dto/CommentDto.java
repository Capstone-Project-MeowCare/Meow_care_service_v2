package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Comment}
 */
public record CommentDto(UUID id,
                         String content,
                         @Size(max = 255) String mediaUrl,
                         @Size(max = 50) String mediaType,
                         Instant createdAt,
                         Instant updatedAt) {
}