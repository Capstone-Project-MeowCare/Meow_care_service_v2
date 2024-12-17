package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.enums.CancelBookingStatus;

import java.util.UUID;

/**
 * DTO for {@link CancelBooking}
 */
public record CancelBookingResponseDto(
        UUID id,
        String reason,
        CancelBookingStatus status,
        Integer requestedAt,
        Integer ownerApprovalAt,
        Integer sitterApprovalAt,
        BookingOrderDto bookingOrder
) {
}