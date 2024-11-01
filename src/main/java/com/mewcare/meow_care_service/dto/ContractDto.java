package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Contract}
 */
public record ContractDto(UUID id,
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
                          Instant createdAt,
                          Instant updatedAt) {
}