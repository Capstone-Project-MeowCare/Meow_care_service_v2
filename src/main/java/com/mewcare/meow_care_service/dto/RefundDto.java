package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Refund}
 */
public record RefundDto(UUID id,
                        BigDecimal refundAmount,
                        String refundReason,
                        @Size(max = 20) String refundStatus,
                        Instant createdAt,
                        Instant processedAt) {
}