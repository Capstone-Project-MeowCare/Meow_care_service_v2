package com.meow_care.meow_care_service.dto;

import lombok.Builder;

@Builder
public record MomoPaymentReturnDto(
        String orderType,
        int amount,
        String partnerCode,
        String orderId,
        String extraData,
        String signature,
        long transId,
        long responseTime,
        int resultCode,
        String message,
        String payType,
        String requestId,
        String orderInfo
) {
}
