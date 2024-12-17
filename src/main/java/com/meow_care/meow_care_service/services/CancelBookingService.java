package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.CancelBooking;

import java.util.UUID;

public interface CancelBookingService {
    CancelBooking createCancelBookingInternal(CancelBooking cancelBooking);

    //create cancel booking
    ApiResponse<CancelBookingResponseDto> createCancelBooking(CancelBookingRequestDto cancelBookingRequestDto);

    //approve cancel booking
    ApiResponse<CancelBookingResponseDto> approveCancelBooking(UUID cancelBookingId);

    //reject cancel booking
    ApiResponse<CancelBookingResponseDto> rejectCancelBooking(UUID cancelBookingId);
}
