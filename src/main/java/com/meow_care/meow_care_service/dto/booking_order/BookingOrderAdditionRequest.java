package com.meow_care.meow_care_service.dto.booking_order;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.entities.BookingOrder;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link BookingOrder}
 */
public class BookingOrderAdditionRequest {
    UUID id;
    Set<BookingDetailDto> bookingDetails;
}
