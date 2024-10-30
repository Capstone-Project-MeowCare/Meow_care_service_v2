package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Service}
 */
public record ServiceDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Size(max = 150) String serviceName,
        @Size(max = 50) String serviceType,
        String actionDescription,
        Integer price,
        Integer duration,
        Integer startTime,
        Integer status,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID configServiceId
) {
}