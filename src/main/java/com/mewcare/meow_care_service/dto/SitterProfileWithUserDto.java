package com.mewcare.meow_care_service.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.mewcare.meow_care_service.entities.SitterProfile}
 */
public record SitterProfileWithUserDto(UUID id,
                                       UserDto user,
                                       String bio,
                                       String experience,
                                       @Size(max = 150) String skill,
                                       BigDecimal rating,
                                       @Size(max = 255) String location,
                                       @Size(max = 255) String environment,
                                       Integer maximumQuantity,
                                       Integer status,
                                       Instant createdAt,
                                       Instant updatedAt) {
}