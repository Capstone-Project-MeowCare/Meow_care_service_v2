package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.BookingOrderDto;
import com.mewcare.meow_care_service.dto.BookingOrderWithDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.BookingOrder;
import com.mewcare.meow_care_service.services.base.BaseService;

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

    ApiResponse<BookingOrderDto> updateStatus(UUID id, Integer status);
}
