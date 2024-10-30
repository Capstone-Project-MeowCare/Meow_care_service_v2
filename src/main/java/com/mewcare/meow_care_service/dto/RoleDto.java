package com.mewcare.meow_care_service.dto;

import com.mewcare.meow_care_service.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Role}
 */
public record RoleDto(
        @Schema(hidden = true)
        UUID id,
        RoleName roleName) {
}