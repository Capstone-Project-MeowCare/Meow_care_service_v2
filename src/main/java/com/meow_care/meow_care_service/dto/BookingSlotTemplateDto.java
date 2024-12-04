package com.meow_care.meow_care_service.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.BookingSlotTemplate}
 */
public record BookingSlotTemplateDto(
        UUID id,
        String name,
        Integer duration,
        Instant startTime,
        Instant endTime
) {
}