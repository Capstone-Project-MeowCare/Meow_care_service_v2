package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Post}
 */
public record PostDto(UUID id,
                      @Size(max = 150) String title,
                      String content,
                      @Size(max = 255) String imageUrl,
                      Instant createdAt,
                      Instant updatedAt) {
}