package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import com.meow_care.meow_care_service.enums.UnavailableDateType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link SitterUnavailableDate}
 */
public record SitterUnavailableDateDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        LocalDate startDate,
        LocalDate endDate,
        LocalDate date,
        @Size(max = 20) String dayOfWeek,
        Boolean isRecurring,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,
        UnavailableDateType type
) {
}