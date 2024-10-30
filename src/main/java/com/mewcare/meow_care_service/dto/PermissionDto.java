package com.mewcare.meow_care_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.Permission}
 */
public record PermissionDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 50) String permissionName,
        @Size(max = 150) String description
) implements Serializable {
}