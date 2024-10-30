package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.BookingOrderDto;
import com.mewcare.meow_care_service.dto.BookingOrderWithDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.BookingOrderService;
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
@RequestMapping("/api/booking-orders")
@RequiredArgsConstructor
public class BookingOrderController {

    private final BookingOrderService bookingOrderService;

    @GetMapping("/{id}")
    public ApiResponse<BookingOrderDto> getBookingOrderById(@PathVariable UUID id) {
        return bookingOrderService.get(id);
    }

    @PostMapping
    public ApiResponse<BookingOrderDto> createBookingOrder(@RequestBody BookingOrderDto bookingOrderDto) {
        return bookingOrderService.create(bookingOrderDto);
    }

    @PostMapping("/with-details")
    public ApiResponse<BookingOrderWithDetailDto> createBookingOrderWithDetails(@RequestBody BookingOrderWithDetailDto bookingOrderWithDetailDto) {
        return bookingOrderService.createWithDetail(bookingOrderWithDetailDto);
    }

    @GetMapping
    public ApiResponse<List<BookingOrderDto>> getAllBookingOrders() {
        return bookingOrderService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookingOrderDto> updateBookingOrder(@PathVariable UUID id, @RequestBody BookingOrderDto bookingOrderDto) {
        return bookingOrderService.update(id, bookingOrderDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBookingOrder(@PathVariable UUID id) {
        return bookingOrderService.delete(id);
    }
}
