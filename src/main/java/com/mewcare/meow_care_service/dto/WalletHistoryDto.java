package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.WalletHistory}
 */
public record WalletHistoryDto(UUID id,
                               @NotNull BigDecimal amount,
                               @Size(max = 50) String type,
                               String description,
                               BigDecimal balanceBeforeTransaction,
                               Instant createdAt) {
}