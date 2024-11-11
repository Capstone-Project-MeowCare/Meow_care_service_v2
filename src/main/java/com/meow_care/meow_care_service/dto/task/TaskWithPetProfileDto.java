package com.meow_care.meow_care_service.dto.task;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record TaskWithPetProfileDto(
        UUID id,
        String description,
        Instant startTime,
        Instant endTime,
        Integer status,
        Instant createdAt,
        Instant updatedAt,
        Set<TaskDto> petProfiles
) {
}
