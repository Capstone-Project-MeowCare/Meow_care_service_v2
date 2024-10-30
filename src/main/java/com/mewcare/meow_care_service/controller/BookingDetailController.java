package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.BookingDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.BookingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking-details")
@RequiredArgsConstructor
public class BookingDetailController {

    private final BookingDetailService bookingDetailService;

    @GetMapping("/{id}")
    public ApiResponse<BookingDetailDto> getBookingDetailById(@PathVariable UUID id) {
        return bookingDetailService.get(id);
    }

    @PostMapping
    public ApiResponse<BookingDetailDto> createBookingDetail(@RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.create(bookingDetailDto);
    }

    @GetMapping
    public ApiResponse<List<BookingDetailDto>> getAllBookingDetails() {
        return bookingDetailService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookingDetailDto> updateBookingDetail(@PathVariable UUID id, @RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.update(id, bookingDetailDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBookingDetail(@PathVariable UUID id) {
        return bookingDetailService.delete(id);
    }

}
