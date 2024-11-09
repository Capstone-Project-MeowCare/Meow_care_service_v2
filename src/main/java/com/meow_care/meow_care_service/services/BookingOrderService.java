package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.BookingOrderDto;
import com.meow_care.meow_care_service.dto.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.services.base.BaseService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface BookingOrderService extends BaseService<BookingOrderDto, BookingOrder> {

    //get with detail
    ApiResponse<BookingOrderWithDetailDto> getWithDetail(UUID id);

    ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderWithDetailDto dto);

    //get by user id
    ApiResponse<List<BookingOrderWithDetailDto>> getByUserId(UUID id);

    //get by sitter id
    ApiResponse<List<BookingOrderWithDetailDto>> getBySitterId(UUID id);

    ApiResponse<BookingOrderDto> updateStatus(UUID id, BookingOrderStatus status);

    ApiResponse<PaymentResponse> createPaymentUrl(UUID id, RequestType requestType) throws Exception;
}
