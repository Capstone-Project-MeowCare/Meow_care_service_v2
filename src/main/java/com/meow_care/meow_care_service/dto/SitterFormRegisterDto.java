package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.SitterFormRegister;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for {@link SitterFormRegister}
 */
public record SitterFormRegisterDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        UUID userId,
        String fullName,
        String email,
        String phoneNumber,
        String address
) {
}