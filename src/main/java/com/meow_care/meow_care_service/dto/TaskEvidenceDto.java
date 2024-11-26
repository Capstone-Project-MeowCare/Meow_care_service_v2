package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.TaskEvidence;
import com.meow_care.meow_care_service.enums.EvidenceType;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link TaskEvidence}
 */
public record TaskEvidenceDto(
        UUID id,
        @Size(max = 255) String photoUrl,
        @Size(max = 255) String videoUrl,
        EvidenceType evidenceType
) {
}