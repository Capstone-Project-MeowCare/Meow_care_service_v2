package com.mewcare.meow_care_service.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Review}
 */
public record ReviewDto(UUID id,
                        Integer rating,
                        String comments,
                        Instant createdAt,
                        Instant updatedAt) {
}