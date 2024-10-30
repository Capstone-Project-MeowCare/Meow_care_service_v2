package com.mewcare.meow_care_service.exception;

import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.enums.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ApiResponse handleRuntimeException(RuntimeException exception) {
        log.error("Unexpected error occurred", exception);
        var errorDetails = new ErrorDetails(exception.getMessage(), String.valueOf(exception.getCause()), exception.getLocalizedMessage());
        return ApiResponse.error(ApiStatus.ERROR, errorDetails);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ApiResponse handleAccessDeniedException() {
        return ApiResponse.status(ApiStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(ApiException.class)
    ApiResponse handleApiException(ApiException exception) {
        return ApiResponse.status(exception.getApiStatus()).message(exception.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var fieldError = Objects.requireNonNull(exception.getFieldError());
        var errorDetails = new ValidationErrorDetails(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
        return ApiResponse.error(ApiStatus.VALIDATION_ERROR, errorDetails);
    }

}
