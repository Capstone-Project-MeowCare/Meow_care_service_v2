package com.meow_care.meow_care_service.dto.care_schedule;

import com.meow_care.meow_care_service.entities.CareSchedule;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link CareSchedule}
 */
@Builder
public record CareScheduleDto(
        UUID id,
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