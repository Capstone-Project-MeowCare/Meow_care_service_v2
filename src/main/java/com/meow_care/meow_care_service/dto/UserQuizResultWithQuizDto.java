package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.UserQuizResult;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link UserQuizResult}
 */
public record UserQuizResultWithQuizDto(
        UUID id,
        QuizDto quiz,
        @NotNull BigDecimal score,
        Instant startTime,
        Instant endTime,
        Integer attempt,
        Duration timeTaken,
        Instant createdAt,
        Instant updatedAt
) {
}