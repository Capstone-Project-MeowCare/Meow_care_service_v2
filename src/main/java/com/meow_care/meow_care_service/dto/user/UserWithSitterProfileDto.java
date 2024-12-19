package com.meow_care.meow_care_service.dto.user;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.enums.UserStatus;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.meow_care.meow_care_service.entities.User}
 */
public record UserWithSitterProfileDto(
        UUID id,
        @Size(max = 100) String email,
        @Size(max = 255) String fullName,
        @Size(max = 255) String avatar,
        @Size(max = 15) String phoneNumber,
        Instant dob,
        @Size(max = 20) String gender,
        @Size(max = 255) String address,
        Instant registrationDate,
        UserStatus status,
        SitterProfileDto sitterProfile
) {
}