package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link SitterProfile}
 */
@Builder
public record SitterProfileDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        UUID sitterId,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String fullName,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String avatar,

        String bio,

        String experience,

        String skill,

        BigDecimal rating,

        @Size(max = 255)
        String location,

        @Size(max = 255)
        String environment,

        Integer maximumQuantity,

        SitterProfileStatus status,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,

        Set<ProfilePictureDto> profilePictures,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        Double distance,

        Integer fullRefundDay
) {
}