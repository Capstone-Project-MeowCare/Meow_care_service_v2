package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.PetProfile}
 */
public record PetProfileDto(
        @Schema(hidden = true)
        UUID id,
        String description,
        @NotNull @Size(max = 50) String petName,
        @Size(max = 50) String species,
        @Size(max = 50) String breed,
        Integer age,
        @Size(max = 10) String gender,
        BigDecimal weight,
        String specialNeeds,
        Boolean vaccinationStatus,
        String vaccinationInfo,
        @Size(max = 50) String microchipNumber,
        String medicalConditions,
        @Size(max = 255) String profilePicture,
        Integer status,
        @Schema(hidden = true)
        Instant createdAt,
        @Schema(hidden = true)
        Instant updatedAt

) {
}