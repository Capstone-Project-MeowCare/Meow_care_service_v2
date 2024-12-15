package com.meow_care.meow_care_service.dto;

import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.Report}
 */
public record ReportDto(
        UUID id,

        UUID userId,

        String userEmail,

        UUID reportedUserId,

        String reportedUserEmail,

        UUID reportTypeId,

        String reason,

        String description
) {
}