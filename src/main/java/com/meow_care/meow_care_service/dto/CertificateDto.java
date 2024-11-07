package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Certificate;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Certificate}
 */
public record CertificateDto(UUID id,
                             @Size(max = 100) String certificateName,
                             @Size(max = 100) String institutionName,
                             Instant issueDate,
                             Instant expiryDate,
                             @Size(max = 255) String certificateUrl,
                             String description,
                             Instant createdAt,
                             Instant updatedAt) {
}