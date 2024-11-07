package com.meow_care.meow_care_service.dto;

import com.meow_care.meow_care_service.entities.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Permission}
 */
public record PermissionDto(
        @Schema(hidden = true)
        UUID id,
        @Size(max = 50) String permissionName,
        @Size(max = 150) String description
) implements Serializable {
}