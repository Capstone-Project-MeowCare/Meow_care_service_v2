package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.UserQuizResult}
 */
public record UserQuizResultDto(UUID id,
                                @NotNull BigDecimal score,
                                Instant startTime,
                                Instant endTime,
                                Integer attempt,
                                Duration timeTaken) {
}