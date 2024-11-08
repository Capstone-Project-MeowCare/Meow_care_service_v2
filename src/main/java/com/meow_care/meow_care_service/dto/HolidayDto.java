package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Holiday;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link Holiday}
 */
public record HolidayDto(
        UUID id,
        @Size(max = 100) String holidayName,
        LocalDate holidayDate,
        String description
) {
}