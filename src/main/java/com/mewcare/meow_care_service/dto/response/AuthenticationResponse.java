package com.mewcare.meow_care_service.dto.response;

import com.mewcare.meow_care_service.dto.UserWithRoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Date tokenExpiresAt;
    private String refreshToken;
    private Date refreshTokenExpiresAt;
    private UserWithRoleDto user;
}
