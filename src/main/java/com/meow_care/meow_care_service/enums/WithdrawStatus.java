package com.meow_care.meow_care_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WithdrawStatus {

    PAID("ĐÃ THANH TOÁN"),
    PAID_FAIL("THANH TOÁN THẤT BẠI"),
    PENDING("CHỜ THANH TOÁN");

    private final String message;

    WithdrawStatus(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
