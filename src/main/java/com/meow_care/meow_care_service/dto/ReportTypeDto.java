package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.ReportType;

import java.util.UUID;

/**
 * DTO for {@link ReportType}
 */
public record ReportTypeDto(
        UUID id,
        String name,
        String description
) {
}