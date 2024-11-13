package com.meow_care.meow_care_service.dto.response;

import com.meow_care.meow_care_service.enums.ApiStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Getter
public class ApiResponse<T> extends ResponseEntity<ResponseBody<T>> {


    public ApiResponse(ResponseBody<T> body, HttpStatusCode status) {
        super(body, status);
    }

    public static ApiResponseBuilder status(ApiStatus status) {
        return ApiResponse.builder()
                .status(status.getCode())
                .statusCode(status.getHttpStatus())
                .message(status.getMessage())
                .timestamp(Instant.now());
    }

    public static <T> ApiResponse<T> noBodyContent() {
        return ApiResponse.status(ApiStatus.NO_CONTENT).build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.status(ApiStatus.SUCCESS).build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.status(ApiStatus.SUCCESS).data(data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.status(ApiStatus.CREATED).data(data);
    }

    public static <T> ApiResponse<T> updated() {
        return ApiResponse.status(ApiStatus.UPDATED).build();
    }

    public static <T> ApiResponse<T> updated(T data) {
        return ApiResponse.status(ApiStatus.UPDATED).data(data);
    }

    public static <T> ApiResponse<T> deleted() {
        return ApiResponse.status(ApiStatus.DELETED).build();
    }

    @SuppressWarnings("unused")
    public static <T> ApiResponse<T> error() {
        return ApiResponse.status(ApiStatus.ERROR).build();
    }

    public static <T> ApiResponse<T> error(ApiStatus status, Object error) {
        return ApiResponse
                .status(status)
                .error(error)
                .build();
    }

    public static ApiResponseBuilder builder() {
        return new ApiResponseBuilder();
    }

    public static class ApiResponseBuilder {
        private int status = ApiStatus.SUCCESS.getCode();
        private HttpStatusCode statusCode = HttpStatus.OK;
        private String message = ApiStatus.SUCCESS.getMessage();
        private Object error;
        private Instant timestamp;

        ApiResponseBuilder() {
        }

        public ApiResponseBuilder status(final int status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder statusCode(final HttpStatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ApiResponseBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder error(final Object error) {
            this.error = error;
            return this;
        }

        public ApiResponseBuilder timestamp(final Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }


        public <T> ApiResponse<T> build() {
            ResponseBody<T> body = new ResponseBody<>();
            body.setStatus(this.status);
            body.setMessage(this.message);
            body.setError(this.error);
            body.setTimestamp(this.timestamp);
            return new ApiResponse<>(body, this.statusCode);
        }

        public <T> ApiResponse<T> data(T data) {
            ResponseBody<T> body = new ResponseBody<>();
            body.setStatus(this.status);
            body.setMessage(this.message);
            body.setError(this.error);
            body.setTimestamp(this.timestamp);
            body.setData(data);
            return new ApiResponse<>(body, this.statusCode);
        }

    }
}
