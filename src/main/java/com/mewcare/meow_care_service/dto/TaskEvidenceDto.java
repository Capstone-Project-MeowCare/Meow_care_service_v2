package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.TaskEvidence}
 */
public record TaskEvidenceDto(UUID id,
                              @Size(max = 255) String photoUrl,
                              @Size(max = 255) String videoUrl,
                              @Size(max = 255) String comment) {
}