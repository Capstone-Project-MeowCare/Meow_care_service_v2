package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.enums.ConfigKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.AppSaveConfig}
 */
@Builder
public record AppSaveConfigDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        ConfigKey configKey,
        String configValue,
        String description,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String createdBy,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String updatedBy,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt
) {
}