package com.meow_care.meow_care_service.dto.booking_order;

import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link BookingOrder}
 */
public record BookingOrderDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID sitterId,

        Instant startDate,

        Instant endDate,

        Integer numberOfPet,

        @NotNull
        @Size(max = 255)
        String name,

        @NotNull
        @Size(max = 15)
        String phoneNumber,

        @NotNull
        @Size(max = 255)
        String address,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer paymentStatus,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,

        String note,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        BookingOrderStatus status
) {
}