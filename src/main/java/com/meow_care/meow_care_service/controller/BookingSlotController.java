package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.BookingSlotDto;
import com.meow_care.meow_care_service.dto.BookingSlotTemplateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
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

import java.time.Instant;
import java.time.LocalDate;
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

    //get all booking slot template by service id and date booking
    @Operation(summary = "Get all booking slot template by service id and date booking")
    @GetMapping("/by-service-id-and-time")
    public ApiResponse<List<BookingSlotTemplateDto>> getByServiceIdAndTime(@RequestParam UUID serviceId, @RequestParam Instant startDate, @RequestParam Instant endDate) {
        return bookingSlotService.getByServiceIdAndTime(serviceId, startDate, endDate);
    }

    //get all booking slot by sitter id, date and status
    @Operation(summary = "Get all booking slot by sitter id, date and status",
               description = "Example: sitterId=123e4567-e89b-12d3-a456-426614174000, date=2024-12-01, status=AVAILABLE")
    @GetMapping("/sitter-booking-slots")
    public ApiResponse<List<BookingSlotDto>> getBySitterIdDateAndStatus(@RequestParam UUID sitterId, @RequestParam LocalDate date, @RequestParam BookingSlotStatus status) {
        return bookingSlotService.getBySitterIdDateAndStatus(sitterId, date, status);
    }

    //get all booking slot by sitter id, service id, date and status
    @Operation(summary = "Get all booking slot by sitter id, service id, date and status",
               description = "Example: sitterId=123e4567-e89b-12d3-a456-426614174000, serviceId=123e4567-e89b-12d3-a456-426614174001, date=2024-12-01, status=AVAILABLE")
    @GetMapping("/sitter-booking-slots-by-service")
    public ApiResponse<List<BookingSlotDto>> getBySitterIdServiceIdDateAndStatus(@RequestParam UUID sitterId,
                                                                                 @RequestParam UUID serviceId,
                                                                                 @RequestParam LocalDate date,
                                                                                 @RequestParam BookingSlotStatus status) {
        return bookingSlotService.getBySitterIdServiceIdDateAndStatus(sitterId, serviceId, date, status);
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

    @Operation(summary = "Update booking slot status")
    @PostMapping("/update-status")
    public ApiResponse<Void> updateStatus(@RequestParam UUID bookingSlotId, @RequestParam BookingSlotStatus status) {
        return bookingSlotService.updateStatusById(bookingSlotId, status);
    }

}
