package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Transaction}
 */
@Builder
public record TransactionDto(
        UUID id,
        BigDecimal amount,
        @Size(max = 10) String currency,
        @Size(max = 50) String paymentMethod,
        @Size(max = 50) String transactionType,
        BigDecimal walletAmount,
        TransactionStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt,
        UUID fromUserId,
        String fromUserEmail,
        UUID toUserId,
        String toUserEmail
) {
}