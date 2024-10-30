package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.ConfigService}
 */
public record ConfigServiceDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        Boolean isRequired,
        Integer ceilPrice,
        Integer floorPrice,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID serviceTypeId
) {
}