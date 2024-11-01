package com.mewcare.meow_care_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Wallet}
 */
public record WalletDto(UUID id,
                        BigDecimal balance,
                        Instant createdAt,
                        Instant updatedAt) {
}