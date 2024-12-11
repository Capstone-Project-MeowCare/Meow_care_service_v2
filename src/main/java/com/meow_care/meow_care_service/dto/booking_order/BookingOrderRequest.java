package com.meow_care.meow_care_service.dto.booking_order;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.dto.BookingDetailWithPetAndServiceDto;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record BookingOrderRequest(
        Instant time,

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

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer paymentStatus,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        BookingOrderStatus status,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,

        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID sitterId,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Set<BookingDetailWithPetAndServiceDto> bookingDetailWithPetAndServices,

        @Valid
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        Set<BookingDetailDto> bookingDetails,

        @NotNull
        Boolean isHouseSitting,

        @NotNull
        OrderType orderType,

        @NotNull
        PaymentMethod paymentMethod
) {
}
