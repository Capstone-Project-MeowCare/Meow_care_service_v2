package com.mewcare.meow_care_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {
    private int status;
    private String message;
    private T data;
    private Object error;
    private LocalDateTime timestamp;
}
