package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.enums.BookingSlotStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.BookingSlot}
 */
public record BookingSlotDto(
        UUID id,
        Integer duration,
        Instant startTime,
        Instant endTime,
        BookingSlotStatus status
) {
}