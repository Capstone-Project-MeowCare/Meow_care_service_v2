package com.meow_care.meow_care_service.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meow_care.meow_care_service.dto.response.ResponseBody;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Instant;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.error(authException.getMessage());

        // Build ResponseBody object with error details
        ResponseBody<Object> responseBody = ResponseBody.<Object>builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Unauthorized")
                .error(authException.getMessage())
                .timestamp(Instant.now())
                .build();

        // Convert ResponseBody to JSON and set response details
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
