package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.BookingOrder}
 */
public record BookingOrderWithDetailDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        Set<BookingDetailDto> bookingDetails,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID sitterId,
        Instant time,
        Instant startDate,
        Instant endDate,
        Integer numberOfPet,
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(max = 15) String phoneNumber,
        @NotNull @Size(max = 255) String address,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer paymentStatus,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer status,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt) {
}