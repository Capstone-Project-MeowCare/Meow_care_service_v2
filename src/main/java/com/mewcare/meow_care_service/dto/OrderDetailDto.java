package com.mewcare.meow_care_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.OrderDetail}
 */
public record OrderDetailDto(UUID id,
                             Integer quantity,
                             BigDecimal price,
                             BigDecimal subtotal,
                             Instant createdAt,
                             Instant updatedAt) {
}