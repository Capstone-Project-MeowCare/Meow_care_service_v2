package com.meow_care.meow_care_service.exception;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.ApiStatus;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ApiResponse<Void> handleRuntimeException(RuntimeException exception) {
        log.error("Unexpected error occurred", exception);
        var errorDetails = new ErrorDetails(exception.getMessage(), String.valueOf(exception.getCause()), exception.getLocalizedMessage());
        return ApiResponse.error(ApiStatus.ERROR, errorDetails);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<String> handleAuthenticationServiceException(AuthenticationServiceException ex) {
        return new ResponseEntity<>("Invalid token: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ApiResponse<Void> handleAccessDeniedException() {
        return ApiResponse.status(ApiStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(ApiException.class)
    ApiResponse<Void> handleApiException(ApiException exception) {
        return ApiResponse.status(exception.getApiStatus()).message(exception.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var fieldError = Objects.requireNonNull(exception.getFieldError());
        var errorDetails = new ValidationErrorDetails(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
        return ApiResponse.error(ApiStatus.VALIDATION_ERROR, errorDetails);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException exception) {
        var violation = exception.getConstraintViolations().stream().findFirst().orElseThrow();
        var errorDetails = new ValidationErrorDetails(violation.getPropertyPath().toString(), violation.getMessage(), violation.getMessage());
        return ApiResponse.error(ApiStatus.VALIDATION_ERROR, errorDetails);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ApiResponse<Void> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String message = exception.getMostSpecificCause().getMessage();
        var errorDetails = new ErrorDetails(
                message.contains("duplicate") ? "Duplicate entry" : "Data integrity violation",
                message,
                null
        );
        return ApiResponse.error(
                message.contains("duplicate") ? ApiStatus.DUPLICATE : ApiStatus.VALIDATION_ERROR,
                errorDetails
        );
    }

}
