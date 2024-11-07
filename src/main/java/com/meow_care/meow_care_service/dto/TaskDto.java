package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Task;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Task}
 */
public record TaskDto(UUID id,
                      String description,
                      Instant startTime,
                      Instant endTime,
                      Integer status,
                      Instant createdAt,
                      Instant updatedAt) {
}