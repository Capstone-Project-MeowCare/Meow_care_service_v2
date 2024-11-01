package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Holiday}
 */
public record HolidayDto(UUID id,
                         @Size(max = 100) String holidayName,
                         LocalDate holidayDate,
                         String description) {
}