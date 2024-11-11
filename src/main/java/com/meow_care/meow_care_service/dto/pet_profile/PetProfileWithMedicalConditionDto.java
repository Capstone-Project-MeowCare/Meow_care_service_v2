package com.meow_care.meow_care_service.dto.pet_profile;

import com.meow_care.meow_care_service.dto.MedicalConditionDto;
import com.meow_care.meow_care_service.entities.PetProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link PetProfile}
 */
public record PetProfileWithMedicalConditionDto(
        @Schema(hidden = true)
        UUID id,
        String description,
        @NotNull @Size(max = 50) String petName,
        @Size(max = 50) String breed,
        Integer age,
        @Size(max = 10) String gender,
        BigDecimal weight,
        @Size(max = 255) String profilePicture,
        Integer status,
        @Schema(hidden = true)
        Instant createdAt,
        @Schema(hidden = true)
        Instant updatedAt,
        Set<MedicalConditionDto> medicalConditions
) {
}
