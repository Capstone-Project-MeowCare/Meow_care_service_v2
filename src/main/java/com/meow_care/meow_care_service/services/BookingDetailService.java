package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.services.base.BaseService;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface BookingDetailService extends BaseService<BookingDetailDto, BookingDetail> {

    ApiResponse<List<BookingDetailDto>> addAdditionBookingDetail(UUID bookingId, List<BookingDetailDto> detailDtos);

    ApiResponse<PaymentResponse> createPaymentUrlAdditionBookingDetail(UUID bookingId, RequestType requestType, String callBackUrl, String redirectUrl) throws Exception;

    ApiResponse<Void> momoCallback(MomoPaymentReturnDto momoPaymentReturnDto);
}
