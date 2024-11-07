package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Wallet;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Wallet}
 */
public record WalletDto(UUID id,
                        BigDecimal balance,
                        Instant createdAt,
                        Instant updatedAt) {
}