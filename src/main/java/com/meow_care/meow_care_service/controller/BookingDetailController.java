package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.BookingDetailService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-details")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class BookingDetailController {

    @Value("${app.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final BookingDetailService bookingDetailService;

    @GetMapping("/{id}")
    public ApiResponse<BookingDetailDto> getBookingDetailById(@PathVariable UUID id) {
        return bookingDetailService.get(id);
    }

    @PostMapping
    public ApiResponse<BookingDetailDto> createBookingDetail(@RequestBody BookingDetailDto bookingDetailDto) {
        return bookingDetailService.create(bookingDetailDto);
    }

    @PostMapping("/add-addition")
    public ApiResponse<List<BookingDetailDto>> addAdditionBookingDetail(@RequestParam UUID bookingId, @RequestBody List<BookingDetailDto> detailDtos) {
        return bookingDetailService.addAdditionBookingDetail(bookingId, detailDtos);
    }

    @PostMapping("/create-payment-url")
    public ApiResponse<PaymentResponse> createPaymentUrlAdditionBookingDetail(@RequestParam UUID bookingId, @RequestParam RequestType requestType, @RequestParam String redirectUrl) throws Exception {
        String callBackUrl = domain + contextPath + "/booking-details/momo-payment-callback";
        return bookingDetailService.createPaymentUrlAdditionBookingDetail(bookingId, requestType, callBackUrl, redirectUrl);
    }

    //momo payment callback
    @PostMapping("/momo-payment-callback")
    public ApiResponse<Void> momoPaymentCallback(@RequestBody MomoPaymentReturnDto momoPaymentReturnDto) {
        return bookingDetailService.momoCallback(momoPaymentReturnDto);
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
