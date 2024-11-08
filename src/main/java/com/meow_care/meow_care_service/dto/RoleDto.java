package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Role;
import com.meow_care.meow_care_service.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO for {@link Role}
 */
public record RoleDto(
        @Schema(hidden = true)
        UUID id,
        RoleName roleName
) {
}