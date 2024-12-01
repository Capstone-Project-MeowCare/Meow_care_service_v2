package com.meow_care.meow_care_service.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meow_care.meow_care_service.dto.response.ResponseBody;
import com.meow_care.meow_care_service.enums.ApiStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Instant;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        String errorMessage = authException.getMessage();

        if(authException.getMessage().contains("expiresAt must be after issuedAt")) {
            errorMessage = "Token expired";
        }

        if (authException.getMessage().contains("Invalid signature")) {
            errorMessage = "Invalid token signature";
        }

        // Build ResponseBody object with error details
        ResponseBody<Object> responseBody = ResponseBody.builder()
                .status(ApiStatus.UNAUTHORIZED.getCode())
                .message("Unauthorized")
                .error(errorMessage)
                .timestamp(Instant.now())
                .build();

        // Convert ResponseBody to JSON and set response details
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
