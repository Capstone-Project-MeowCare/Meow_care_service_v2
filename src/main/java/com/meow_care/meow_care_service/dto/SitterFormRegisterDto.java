package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.SitterFormRegister;
import com.meow_care.meow_care_service.enums.SitterFormRegisterStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for {@link SitterFormRegister}
 */
public record SitterFormRegisterDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,
        UUID userId,
        String fullName,
        String email,
        String phoneNumber,
        String address,
        SitterFormRegisterStatus status
) {
}