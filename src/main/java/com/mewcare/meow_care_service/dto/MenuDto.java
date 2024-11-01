package com.mewcare.meow_care_service.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Menu}
 */
public record MenuDto(UUID id,
                      String foodItems,
                      String dietaryNotes,
                      Instant createdAt,
                      Instant updatedAt) {
}