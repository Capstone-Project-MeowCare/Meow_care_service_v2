package com.meow_care.meow_care_service.exception;

public record ErrorDetails(
        String message,
        String cause,
        String localizedMessage
) {
}
