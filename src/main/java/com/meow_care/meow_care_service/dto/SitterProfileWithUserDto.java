package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.dto.user.UserDto;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
public record SitterProfileWithUserDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        UserDto user,

        String bio,

        String experience,

        String skill,

        BigDecimal rating,

        @Size(max = 255)
        String location,

        @Size(max = 255)
        String environment,

        Integer maximumQuantity,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        SitterProfileStatus status,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant createdAt,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant updatedAt,

        Set<ProfilePictureDto> profilePictures,

        Double latitude,

        Double longitude,

        Double distance,

        Integer fullRefundDay
) {
}