package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Menu;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Menu}
 */
public record MenuDto(UUID id,
                      String foodItems,
                      String dietaryNotes,
                      Instant createdAt,
                      Instant updatedAt) {
}