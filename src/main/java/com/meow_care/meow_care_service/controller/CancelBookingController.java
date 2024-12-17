package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.CancelBookingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class CancelBookingController {


    //create cancel booking
    @Operation(summary = "Create cancel booking")
    @PostMapping("/create-cancel-booking")
    public ApiResponse<CancelBookingResponseDto> createCancelBooking(@RequestBody CancelBookingRequestDto cancelBookingRequestDto) {
        return ApiResponse.notImplemented();
    }

    //approve cancel booking
    @Operation(summary = "Approve cancel booking")
    @PostMapping("/approve-cancel-booking")
    public ApiResponse<CancelBookingResponseDto> approveCancelBooking(@RequestBody CancelBookingRequestDto cancelBookingRequestDto) {
        return ApiResponse.notImplemented();
    }




}
