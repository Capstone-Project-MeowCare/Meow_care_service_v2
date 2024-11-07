package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.OrderDetail;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link OrderDetail}
 */
public record OrderDetailDto(UUID id,
                             Integer quantity,
                             BigDecimal price,
                             BigDecimal subtotal,
                             Instant createdAt,
                             Instant updatedAt) {
}