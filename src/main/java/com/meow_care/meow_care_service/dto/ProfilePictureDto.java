package com.meow_care.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.ProfilePicture}
 */
public record ProfilePictureDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        String imageName,

        String imageUrl

) {
}