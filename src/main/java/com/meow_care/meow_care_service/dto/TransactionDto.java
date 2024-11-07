package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Transaction;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Transaction}
 */
public record TransactionDto(UUID id,
                             BigDecimal amount,
                             @Size(max = 10) String currency,
                             @Size(max = 50) String paymentMethod,
                             @Size(max = 50) String transactionType,
                             BigDecimal walletAmount,
                             @Size(max = 20) String status,
                             String description,
                             Instant createdAt,
                             Instant updatedAt) {
}