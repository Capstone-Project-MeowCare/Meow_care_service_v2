package com.mewcare.meow_care_service.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Task}
 */
public record TaskDto(UUID id,
                      String description,
                      Instant startTime,
                      Instant endTime,
                      Integer status,
                      Instant createdAt,
                      Instant updatedAt) {
}