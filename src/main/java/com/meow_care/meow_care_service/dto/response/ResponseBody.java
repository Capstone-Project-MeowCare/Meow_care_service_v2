package com.meow_care.meow_care_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBody<T> {
    private int status;
    private String message;
    private T data;
    private Object error;
    private Instant timestamp;
}
