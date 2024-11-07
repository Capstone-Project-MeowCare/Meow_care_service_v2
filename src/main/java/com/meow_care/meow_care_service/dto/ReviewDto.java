package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Review;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Review}
 */
public record ReviewDto(UUID id,
                        Integer rating,
                        String comments,
                        Instant createdAt,
                        Instant updatedAt) {
}