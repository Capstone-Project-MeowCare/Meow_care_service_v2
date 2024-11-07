package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link ServiceType}
 */
public record ServiceTypeDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 50) String type,
        String description,
        @Schema(hidden = true)
        Instant createdAt,
        @Schema(hidden = true)
        Instant updatedAt
) {
}