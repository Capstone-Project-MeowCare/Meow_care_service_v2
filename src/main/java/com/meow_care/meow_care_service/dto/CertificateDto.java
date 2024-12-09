package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Certificate;
import com.meow_care.meow_care_service.enums.CertificateType;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Certificate}
 */
public record CertificateDto(
        UUID id,
        UUID sitterProfileId,
        @Size(max = 100) String certificateName,
        @Size(max = 100) String institutionName,
        Instant issueDate,
        Instant expiryDate,
        @Size(max = 255) String certificateUrl,
        String description,
        Instant createdAt,
        Instant updatedAt,
        CertificateType certificateType
) {
}