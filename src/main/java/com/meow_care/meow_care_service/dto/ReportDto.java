package com.meow_care.meow_care_service.dto;

import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.Report}
 */
public record ReportDto(
        UUID id,

        UUID userId,

        UUID reportedUserId,

        UUID reportTypeId,

        String reason,

        String description
) {
}