package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.MomoPaymentReturnDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface TopUpService {
    ApiResponse<PaymentResponse> topUpByMomo(UUID userId, BigDecimal amount, String redirectUrl, RequestType requestType);

    ApiResponse<Void> handleMomoTopUpCallback(MomoPaymentReturnDto momoPaymentReturnDto);
}
