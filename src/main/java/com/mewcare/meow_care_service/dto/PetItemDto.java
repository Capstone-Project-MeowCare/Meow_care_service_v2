package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.PetItem}
 */
public record PetItemDto(UUID id,
                         @Size(max = 100) String name,
                         String description,
                         BigDecimal price,
                         Integer stock,
                         @Size(max = 50) String category,
                         @Size(max = 255) String imageUrl,
                         Instant createdAt,
                         Instant updatedAt) {
}