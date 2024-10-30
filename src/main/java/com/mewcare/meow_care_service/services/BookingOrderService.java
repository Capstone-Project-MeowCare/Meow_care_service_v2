package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.BookingOrderDto;
import com.mewcare.meow_care_service.dto.BookingOrderWithDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.BookingOrder;
import com.mewcare.meow_care_service.services.base.BaseService;

public interface BookingOrderService extends BaseService<BookingOrderDto, BookingOrder> {
    ApiResponse<BookingOrderWithDetailDto> createWithDetail(BookingOrderWithDetailDto dto);
}
