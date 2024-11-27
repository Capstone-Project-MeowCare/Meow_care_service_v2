package com.meow_care.meow_care_service.dto.task;

import com.meow_care.meow_care_service.entities.Task;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Task}
 */
@Builder
public record TaskDto(
        UUID id,
        String description,
        Instant startTime,
        Instant endTime,
        Integer status,
        Instant createdAt,
        Instant updatedAt,
        String comment,
        Boolean haveEvidence
) {
}

