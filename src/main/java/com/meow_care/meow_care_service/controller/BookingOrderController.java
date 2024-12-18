package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderRequest;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-orders")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class BookingOrderController {

    private final BookingOrderService bookingOrderService;

    @GetMapping("/{id}")
    public ApiResponse<BookingOrderWithDetailDto> getBookingOrderById(@PathVariable UUID id) {
        return bookingOrderService.getWithDetail(id);
    }

    @GetMapping
    public ApiResponse<List<BookingOrderDto>> getAllBookingOrders() {
        return bookingOrderService.getAll();
    }

    //get all pagination
    @GetMapping("/pagination")
    public ApiResponse<Page<BookingOrderDto>> getAllBookingOrdersWithPagination(@RequestParam(defaultValue = "1") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "createdAt") String sort,
                                                                                @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return bookingOrderService.getAll(page - 1, size, sort, direction);
    }

    //get by user id in param
    @GetMapping("/user")
    public ApiResponse<List<BookingOrderWithDetailDto>> getBookingOrderByUserId(@RequestParam UUID id) {
        return bookingOrderService.getByUserId(id);
    }

    //get by sitter id in param
    @GetMapping("/sitter")
    public ApiResponse<List<BookingOrderWithDetailDto>> getBookingOrderBySitterId(@RequestParam UUID id) {
        return bookingOrderService.getBySitterId(id);
    }

    //get by user id with pagination
    @GetMapping("/user/pagination")
    public ApiResponse<Page<BookingOrderWithDetailDto>> getBookingOrderByUserIdWithPagination(@RequestParam UUID id,
                                                                                              @RequestParam(defaultValue = "1") int page,
                                                                                              @RequestParam(defaultValue = "10") int size,
                                                                                              @RequestParam(defaultValue = "createdAt") String sort,
                                                                                              @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return bookingOrderService.getByUserId(id, page - 1, size, sort, direction);
    }

    //get by sitter id with pagination
    @GetMapping("/sitter/pagination")
    public ApiResponse<Page<BookingOrderWithDetailDto>> getBookingOrderBySitterIdWithPagination(@RequestParam UUID id,
                                                                                                @RequestParam(defaultValue = "1") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(defaultValue = "createdAt") String sort,
                                                                                                @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return bookingOrderService.getBySitterId(id, page - 1, size, sort, direction);
    }

    @GetMapping("/user/status")
    public ApiResponse<Page<BookingOrderWithDetailDto>> getByUserIdAndStatus(
            @RequestParam UUID userId,
            @RequestParam(required = false) BookingOrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String prop,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return bookingOrderService.getByUserIdAndStatus(userId, status, page, size, prop, direction);
    }

    @GetMapping("/sitter/status")
    public ApiResponse<Page<BookingOrderWithDetailDto>> getBySitterIdAndStatus(
            @RequestParam UUID sitterId,
            @RequestParam(required = false) BookingOrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String prop,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return bookingOrderService.getBySitterIdAndStatus(sitterId, status, page, size, prop, direction);
    }

    //count booking created in time range
    @GetMapping("/count")
    public ApiResponse<Long> countBookingOrderInTimeRange(@RequestParam Instant from, @RequestParam Instant to) {
        return bookingOrderService.countBookingOrderInTimeRange(from, to);
    }

    //count by status in param
    @GetMapping("/count-by-status")
    public ApiResponse<Long> countBookingOrderByStatus(@RequestParam BookingOrderStatus status, @RequestParam Instant from, @RequestParam Instant to) {
        return bookingOrderService.countByStatusAndUpdatedAtBetween(status, from, to);
    }

    //count by sitter id and status, order type
    @GetMapping("/count-by-sitter")
    public ApiResponse<Long> countBySitterIdAndStatusAndOrderType(@RequestParam UUID id,
                                                                 @RequestParam(required = false) BookingOrderStatus status,
                                                                 @RequestParam(required = false) OrderType orderType) {
        return bookingOrderService.countBySitterIdAndStatusAndOrderType(id, status, orderType);
    }

    //get total price by booking id
    @GetMapping("/total-price")
    public ApiResponse<BigDecimal> getTotalPriceByBookingId(@RequestParam UUID id) {
        return bookingOrderService.getTotalPrice(id);
    }

    //    public boolean isFullSlot(UUID sitterId, Instant startDate, Instant endDate, Integer numberOfPets) {
    //check is full slot
    @GetMapping("/is-full-slot")
    public ApiResponse<Boolean> isFullSlot(@RequestParam UUID sitterId, @RequestParam Instant startDate, @RequestParam Instant endDate, @RequestParam Integer numberOfPets) {
        return bookingOrderService.isFullSlot(sitterId, startDate, endDate, numberOfPets);
    }


    //create payment url  by order id
    @PostMapping("/payment-url")
    public ApiResponse<PaymentResponse> createPaymentUrl(@RequestParam UUID id, @RequestParam RequestType requestType, @RequestParam String redirectUrl) throws Exception {
        return bookingOrderService.createPaymentUrl(id, requestType, redirectUrl);
    }

    //payment callback
    @Operation(hidden = true)
    @PostMapping("/momo-payment-callback")
    public ApiResponse<Void> momoCallback(@RequestBody MomoPaymentReturnDto momoPaymentReturnDto) {
        return bookingOrderService.momoCallback(momoPaymentReturnDto);
    }

    @PostMapping("/with-details")
    public ApiResponse<BookingOrderWithDetailDto> createBookingOrderWithDetails(@Valid @RequestBody BookingOrderRequest bookingOrderRequest) {
        return bookingOrderService.createWithDetail(bookingOrderRequest);
    }

    @PutMapping("/{id}")
    public ApiResponse<BookingOrderDto> updateBookingOrder(@PathVariable UUID id, @Valid @RequestBody BookingOrderDto bookingOrderDto) {
        return bookingOrderService.update(id, bookingOrderDto);
    }

    //update status
    @PutMapping("/status/{id}")
    public ApiResponse<Void> updateBookingOrderStatus(@PathVariable UUID id, @RequestParam BookingOrderStatus status) {
        return bookingOrderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBookingOrder(@PathVariable UUID id) {
        return bookingOrderService.delete(id);
    }
}
