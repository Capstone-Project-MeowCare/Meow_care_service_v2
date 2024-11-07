package com.meow_care.meow_care_service.exception;

public record ValidationErrorDetails(
        String field,
        String code,
        String message
) {
}