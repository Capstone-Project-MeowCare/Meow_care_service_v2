package com.meow_care.meow_care_service.dto.user;

import com.meow_care.meow_care_service.dto.RoleDto;
import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
public record UserWithRoleDto(
        UUID id,
        @NotNull @Size(max = 50) String username,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        @NotNull String password,
        @Size(max = 100) String email,
        @Size(max = 255) String fullName,
        @Size(max = 255) String avatar,
        @Size(max = 15) String phoneNumber,
        Instant dob,
        @Size(max = 20) String gender,
        @Size(max = 255) String address,
        @Schema(hidden = true)
        Instant registrationDate,
        Integer status,
        Set<RoleDto> roles,
        SitterProfileDto sitterProfile
) {
}