package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Reaction;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Reaction}
 */
public record ReactionDto(UUID id,
                          @Size(max = 50) String reactionType,
                          Instant createdAt) {
}