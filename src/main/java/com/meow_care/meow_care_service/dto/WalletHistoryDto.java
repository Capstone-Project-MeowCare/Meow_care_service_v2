package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.WalletHistory;
import com.meow_care.meow_care_service.enums.WalletHistoryType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link WalletHistory}
 */
public record WalletHistoryDto(
        UUID id,
        UUID walletId,
        UUID userId,
        UUID userEmail,
        BigDecimal amount,
        WalletHistoryType type,
        BigDecimal balance,
        Instant createdAt
) {
}