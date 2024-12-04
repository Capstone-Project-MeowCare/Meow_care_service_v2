package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.BookingSlotTemplateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.BookingSlotService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-slots")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class BookingSlotController {

    private final BookingSlotService bookingSlotService;

    //get all booking slot template by user id
    @Operation(summary = "Get all booking slot template by user id")
    @GetMapping
    public ApiResponse<List<BookingSlotTemplateDto>> getAllByUserId(@RequestParam UUID userId) {
        return bookingSlotService.getAllByUserId(userId);
    }

    @Operation(summary = "Create booking slot")
    @PostMapping
    public ApiResponse<BookingSlotTemplateDto> createBookingSlot(@RequestBody BookingSlotTemplateDto bookingSlotTemplateDto) {
        return bookingSlotService.create(bookingSlotTemplateDto);
    }

    @Operation(summary = "Assign service to booking slot")
    @PostMapping("/assign-service")
    public ApiResponse<Void> assignServiceToBookingSlot(@RequestParam UUID bookingSlotTemplateId, @RequestParam UUID serviceId) {
        return bookingSlotService.assignService(bookingSlotTemplateId, serviceId);
    }

}
