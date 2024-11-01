package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Reaction}
 */
public record ReactionDto(UUID id,
                          @Size(max = 50) String reactionType,
                          Instant createdAt) {
}