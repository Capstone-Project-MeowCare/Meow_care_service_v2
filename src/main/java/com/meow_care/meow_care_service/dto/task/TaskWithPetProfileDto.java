package com.meow_care.meow_care_service.dto.task;

import com.meow_care.meow_care_service.dto.pet_profile.PetProfileDto;

import java.time.Instant;
import java.util.UUID;

public record TaskWithPetProfileDto(
        UUID id,
        String description,
        Instant startTime,
        Instant endTime,
        Integer status,
        Instant createdAt,
        Instant updatedAt,
        PetProfileDto petProfile,
        String comment,
        Boolean haveEvidence
) {
}
