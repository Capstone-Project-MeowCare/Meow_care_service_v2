package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.CancelBookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class CancelBookingController {

    private final CancelBookingService cancelBookingService;

    //create cancel booking
    @Operation(summary = "Create cancel booking")
    @PostMapping("/create-cancel-booking")
    public ApiResponse<CancelBookingResponseDto> createCancelBooking(@RequestBody CancelBookingRequestDto cancelBookingRequestDto) {
        return cancelBookingService.createCancelBooking(cancelBookingRequestDto);
    }

    //approve cancel booking by id
    @Operation(summary = "Approve cancel booking")
    @PostMapping("/approve-cancel-booking/{cancelBookingId}")
    public ApiResponse<CancelBookingResponseDto> approveCancelBooking(@PathVariable UUID cancelBookingId) {
        return cancelBookingService.approveCancelBooking(cancelBookingId);
    }

    //reject cancel booking by id
    @Operation(summary = "Reject cancel booking")
    @PostMapping("/reject-cancel-booking/{cancelBookingId}")
    public ApiResponse<CancelBookingResponseDto> rejectCancelBooking(@PathVariable UUID cancelBookingId) {
        return cancelBookingService.rejectCancelBooking(cancelBookingId);
    }



}
