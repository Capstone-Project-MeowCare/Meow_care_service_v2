package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.WalletHistory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link WalletHistory}
 */
public record WalletHistoryDto(
        UUID id,
        @NotNull BigDecimal amount,
        @Size(max = 50) String type,
        String description,
        BigDecimal balanceBeforeTransaction,
        Instant createdAt
) {
}