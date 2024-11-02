package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.SitterProfile}
 */
public record SitterProfileDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String fullName,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String avatar,
        String bio,
        String experience,
        @Size(max = 150) String skill,
        BigDecimal rating,
        @Size(max = 255) String location,
        @Size(max = 255) String environment,
        Integer maximumQuantity,
        Integer status,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt
) {
}