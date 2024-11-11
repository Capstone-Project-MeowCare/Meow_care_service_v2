package com.meow_care.meow_care_service.dto.care_schedule;

import com.meow_care.meow_care_service.dto.task.TaskWithPetProfileDto;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.CareSchedule}
 */
@Builder
public record CareScheduleWithTaskDto(
        UUID id,
        Set<TaskWithPetProfileDto> tasks,
        Instant startTime,
        Instant endTime,
        String sessionNotes,
        @Size(max = 255) String photoUrl,
        @Size(max = 255) String videoCallUrl,
        @Size(max = 255) String reportIssue,
        @Size(max = 50) String status,
        Instant createdAt,
        Instant updatedAt
) {
}