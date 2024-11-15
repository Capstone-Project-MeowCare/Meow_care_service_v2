package com.meow_care.meow_care_service.dto.booking_order;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record BookingOrderRequest(
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

        String note,

        Set<BookingDetailDto> bookingDetails,

        UUID sitterId
) {
}
