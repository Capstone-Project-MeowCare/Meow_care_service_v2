package com.mewcare.meow_care_service.exception;

public record ValidationErrorDetails(
        String field,
        String code,
        String message
) {
}