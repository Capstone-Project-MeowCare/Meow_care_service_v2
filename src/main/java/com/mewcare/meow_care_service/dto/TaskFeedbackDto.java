package com.mewcare.meow_care_service.dto;

import com.mewcare.meow_care_service.entities.Task;
import com.mewcare.meow_care_service.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.TaskFeedback}
 */
public record TaskFeedbackDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        Task task,
        User user,
        int rating,
        String comment,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}