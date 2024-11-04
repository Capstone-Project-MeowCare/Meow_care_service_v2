package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.MedicalCondition}
 */
public record MedicalConditionDto(
        UUID id,
        @NotNull @Size(max = 100) String conditionName,
        @Size(max = 255) String description
) {
}