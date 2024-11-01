package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.CareSchedule}
 */
public record CareScheduleDto(UUID id,
                              Instant startTime,
                              Instant endTime,
                              String sessionNotes,
                              @Size(max = 255) String photoUrl,
                              @Size(max = 255) String videoCallUrl,
                              @Size(max = 255) String reportIssue,
                              @Size(max = 50) String status,
                              Instant createdAt,
                              Instant updatedAt) {
}