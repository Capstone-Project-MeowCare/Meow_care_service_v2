package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.History;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link History}
 */

public record HistoryDto(
        UUID id,
        @Size(max = 50) String eventType,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

}