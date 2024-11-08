package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Order;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Order}
 */
public record OrderDto(
        UUID id,
        BigDecimal totalAmount,
        @Size(max = 50) String status,
        @Size(max = 50) String paymentMethod,
        @Size(max = 255) String shippingAddress,
        Instant createdAt,
        Instant updatedAt
) {
}