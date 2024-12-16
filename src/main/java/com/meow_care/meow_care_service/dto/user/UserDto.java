package com.meow_care.meow_care_service.dto.user;

import com.meow_care.meow_care_service.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
public record UserDto(
        @Schema(hidden = true)
        UUID id,
        @NotNull
        @Size(max = 100)
        String email,
        @NotNull
        @Size(min = 6, max = 50)
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        String password,
        @Size(max = 255) String fullName,
        @Size(max = 255) String avatar,
        @Size(max = 15) String phoneNumber,
        Instant dob,
        @Size(max = 20) String gender,
        @Size(max = 255) String address,
        @Schema(hidden = true)
        Integer status
) {
}