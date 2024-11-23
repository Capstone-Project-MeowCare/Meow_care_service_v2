package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.QuizAnswer;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link QuizAnswer}
 */
public record QuizAnswerDto(
        UUID id,
        @Size(max = 150) String answerText,
        Boolean isCorrect
) {
}