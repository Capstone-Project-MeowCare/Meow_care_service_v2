package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Quiz}
 */
public record QuizDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 150) String title,
        String description,
        Boolean isActive
) {
}