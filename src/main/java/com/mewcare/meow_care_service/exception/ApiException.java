package com.mewcare.meow_care_service.exception;

import com.mewcare.meow_care_service.enums.ApiStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ApiStatus apiStatus;

    public ApiException(ApiStatus apiStatus) {
        super(apiStatus.getMessage());
        this.apiStatus = apiStatus;
    }

    public ApiException(ApiStatus apiStatus, String message) {
        super(message);
        this.apiStatus = apiStatus;
    }

}
