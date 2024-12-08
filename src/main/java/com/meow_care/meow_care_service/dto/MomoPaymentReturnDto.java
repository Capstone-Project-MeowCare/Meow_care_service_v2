package com.meow_care.meow_care_service.dto;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderType", orderType);
        map.put("amount", amount);
        map.put("partnerCode", partnerCode);
        map.put("orderId", orderId);
        map.put("extraData", extraData);
        map.put("transId", transId);
        map.put("responseTime", responseTime);
        map.put("resultCode", resultCode);
        map.put("message", message);
        map.put("payType", payType);
        map.put("requestId", requestId);
        map.put("orderInfo", orderInfo);
        return map;
    }

    @Override
    public String toString() {
        return "MomoPaymentReturnDto{" +
                "orderType='" + orderType + '\'' +
                ", amount=" + amount +
                ", partnerCode='" + partnerCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", extraData='" + extraData + '\'' +
                ", signature='" + signature + '\'' +
                ", transId=" + transId +
                ", responseTime=" + responseTime +
                ", resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", payType='" + payType + '\'' +
                ", requestId='" + requestId + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                '}';
    }
}
