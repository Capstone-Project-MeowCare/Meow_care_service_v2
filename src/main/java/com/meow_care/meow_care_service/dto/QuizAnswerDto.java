package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.QuizAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link QuizAnswer}
 */
public record QuizAnswerDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 150) String answerText,
        Boolean isCorrect
) {
}