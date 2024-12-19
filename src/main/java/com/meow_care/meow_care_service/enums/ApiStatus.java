package com.meow_care.meow_care_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiStatus {

    SUCCESS(1000, "Success", HttpStatus.OK),
    CREATED(1001, "Created", HttpStatus.CREATED),
    UPDATED(1002, "Updated", HttpStatus.OK),
    DELETED(1003, "Deleted", HttpStatus.OK),
    NO_CONTENT(1004, "No Content", HttpStatus.NO_CONTENT),

    ERROR(2001, "Error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(2002, "Not Found", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR(2003, "Validation Error", HttpStatus.BAD_REQUEST),
    FORBIDDEN(2004, "Forbidden", HttpStatus.FORBIDDEN),
    TOKEN_NOT_VALID(2005, "Token not valid", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2006, "Unauthorized", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(2007, "Invalid Credentials", HttpStatus.UNAUTHORIZED),
    DUPLICATE(2008, "Duplicate", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS(2009, "Already Exists", HttpStatus.BAD_REQUEST),
    SIGNATURE_NOT_MATCH(2010, "Signature not match", HttpStatus.BAD_REQUEST),
    PAYMENT_ERROR(2011, "Payment Error", HttpStatus.BAD_REQUEST),
    UPDATE_ERROR(2012, "Update Error", HttpStatus.BAD_REQUEST),
    AMOUNT_NOT_ENOUGH(2013, "Amount not enough", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(2014, "Invalid Request", HttpStatus.BAD_REQUEST),
    NOT_IMPLEMENTED(2015, "Not Implemented", HttpStatus.NOT_IMPLEMENTED),
    CONFLICT(2016, "Conflict", HttpStatus.CONFLICT),
    USER_NOT_ACTIVE(2017, "User not active", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ApiStatus(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
