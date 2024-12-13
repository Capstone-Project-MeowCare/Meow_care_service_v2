package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.dto.task.TaskDto;
import com.meow_care.meow_care_service.dto.user.UserDto;
import com.meow_care.meow_care_service.entities.TaskFeedback;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link TaskFeedback}
 */
public record TaskFeedbackDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        TaskDto task,
        UserDto user,
        int rating,
        String comment,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
}