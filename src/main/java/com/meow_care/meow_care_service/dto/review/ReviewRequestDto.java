package com.meow_care.meow_care_service.dto.review;

import com.meow_care.meow_care_service.entities.Review;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Review}
 */
public record ReviewRequestDto(
        UUID id,
        Integer rating,
        String comments,
        Instant createdAt,
        Instant updatedAt,
        UUID userId,
        UUID bookingOrderId
) {
}