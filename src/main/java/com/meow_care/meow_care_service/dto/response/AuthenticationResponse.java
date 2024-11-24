package com.meow_care.meow_care_service.dto.response;

import com.meow_care.meow_care_service.dto.user.UserWithRoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Instant tokenExpiresAt;
    private String refreshToken;
    private Instant refreshTokenExpiresAt;
    private UserWithRoleDto user;
}
