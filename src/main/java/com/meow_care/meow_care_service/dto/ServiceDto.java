package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Service;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link Service}
 */
public record ServiceDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @Size(max = 150) String serviceName,

        @Size(max = 150) String otherName,

        String additionDescription,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @Size(max = 50) String serviceType,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String actionDescription,

        Integer price,

        @Size(min = 1, max = 1440)
        Integer duration,

        Integer startTime,

        Integer status,

        @Size(max = 24)
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID configServiceId

) {
}