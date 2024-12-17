package com.meow_care.meow_care_service.dto.booking_order;

import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.CancelBooking}
 */
public record CancelBookingRequestDto(
        UUID bookingOrderId,
        String reason
) {
}