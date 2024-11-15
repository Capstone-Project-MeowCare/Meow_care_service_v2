package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.services.base.BaseService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface BookingOrderService extends BaseService<BookingOrderDto, BookingOrder> {

    ApiResponse<BookingOrderWithDetailDto> getWithDetail(UUID id);

    ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderWithDetailDto dto);

    ApiResponse<List<BookingOrderWithDetailDto>> getByUserId(UUID id);

    ApiResponse<List<BookingOrderWithDetailDto>> getBySitterId(UUID id);

    ApiResponse<Void> updateStatus(UUID id, BookingOrderStatus status);

    ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType, String callBackUrl, String redirectUrl) throws Exception;

    ApiResponse<Long> countBookingOrderInTimeRange(Instant createdAtStart, Instant createdAtEnd);

    ApiResponse<Long> countByStatus(BookingOrderStatus status);

    ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto);
}
