package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.SitterUnavailableDate}
 */
public record SitterUnavailableDateDto(UUID id,
                                       Instant startDate,
                                       Instant endDate,
                                       @Size(max = 20) String dayOfWeek,
                                       Boolean isRecurring,
                                       Instant createdAt,
                                       Instant updatedAt) {
}