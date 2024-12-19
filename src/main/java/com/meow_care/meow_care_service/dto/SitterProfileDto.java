package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link SitterProfile}
 */
@Builder
@Getter
@Setter
public final class SitterProfileDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final UUID id;

    private final UUID sitterId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final String fullName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final String avatar;

    private final String bio;

    private final String experience;

    private final String skill;

    private final Double rating;

    private final @Size(max = 255) String location;

    private final @Size(max = 255) String environment;

    private final Integer maximumQuantity;

    private final SitterProfileStatus status;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final Instant createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private final Instant updatedAt;

    private final Set<ProfilePictureDto> profilePictures;

    private final @NotNull Double latitude;

    private final @NotNull Double longitude;

    private final Double distance;

    private final Integer fullRefundDay;

    private final Integer mainServicePrice;

    private Long numberOfReview;
}