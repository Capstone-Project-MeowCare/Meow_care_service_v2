package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.ConfigService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link ConfigService}
 */
public record ConfigServiceDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        Integer ceilPrice,
        Integer floorPrice,
        String name,
        Boolean isBasicService,
        String actionDescription,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID serviceTypeId
) {
}