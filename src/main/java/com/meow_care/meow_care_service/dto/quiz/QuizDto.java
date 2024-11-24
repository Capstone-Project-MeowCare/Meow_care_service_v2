package com.meow_care.meow_care_service.dto.quiz;

import com.meow_care.meow_care_service.entities.Quiz;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link Quiz}
 */
public record QuizDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 150) String title,
        String description,
        Boolean isActive
) {
}