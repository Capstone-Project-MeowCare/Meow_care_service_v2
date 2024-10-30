package com.mewcare.meow_care_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiStatus {

    SUCCESS(1000, "Success", HttpStatus.OK),
    CREATED(1001, "Created", HttpStatus.CREATED),
    UPDATED(1002, "Updated", HttpStatus.OK),
    DELETED(1003, "Deleted", HttpStatus.OK),
    ERROR(2001, "Error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(2002, "Not Found", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR(2003, "Validation Error", HttpStatus.BAD_REQUEST),
    FORBIDDEN(2004, "Forbidden", HttpStatus.FORBIDDEN),
    TOKEN_NOT_VALID(2005, "Token not valid", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2006, "Unauthorized", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ApiStatus(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
