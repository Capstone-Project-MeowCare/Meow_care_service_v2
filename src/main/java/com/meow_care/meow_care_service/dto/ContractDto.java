package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Contract}
 */
public record ContractDto(
        UUID id,
        @Size(max = 100) String ownerName,
        @Size(max = 255) String ownerAddress,
        @Size(max = 15) String ownerPhone,
        @Size(max = 100) String ownerEmail,
        @Size(max = 100) String sitterName,
        @Size(max = 255) String sitterAddress,
        @Size(max = 15) String sitterPhone,
        @Size(max = 100) String sitterEmail,
        String contractTerms,
        BigDecimal totalPrice,
        BigDecimal depositAmount,
        BigDecimal remainingAmount,
        Instant startDate,
        Instant endDate,
        String contractUrl,
        Instant createdAt,
        Instant updatedAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) UUID userId,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY) UUID sitterId
) {
}