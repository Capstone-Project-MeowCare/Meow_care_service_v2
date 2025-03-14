package com.meow_care.meow_care_service.dto.quiz;

import com.meow_care.meow_care_service.entities.QuizQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link QuizQuestion}
 */
public record QuizQuestionDto(
        @Schema(hidden = true)
        UUID id,
        @NotNull String questionText,
        @Size(max = 20) String questionType
) {
}