package com.meow_care.meow_care_service.dto.review;

import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.user.UserDto;
import com.meow_care.meow_care_service.entities.Review;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Review}
 */
public record ReviewResponseDto(
        UUID id,
        Integer rating,
        String comments,
        Instant createdAt,
        Instant updatedAt,
        UserDto user,
        BookingOrderDto bookingOrder
) {
}