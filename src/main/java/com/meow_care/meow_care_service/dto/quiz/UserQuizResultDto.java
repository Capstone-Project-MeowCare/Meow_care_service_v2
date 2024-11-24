package com.meow_care.meow_care_service.dto.quiz;

import com.meow_care.meow_care_service.entities.UserQuizResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link UserQuizResult}
 */
public record UserQuizResultDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @NotNull BigDecimal score,
        Instant startTime,
        Instant endTime,
        Integer attempt,
        Duration timeTaken,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt
) {
}