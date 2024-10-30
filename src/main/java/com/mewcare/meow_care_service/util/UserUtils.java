package com.mewcare.meow_care_service.util;

import com.mewcare.meow_care_service.enums.ApiStatus;
import com.mewcare.meow_care_service.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class UserUtils {
    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return UUID.fromString(jwt.getClaimAsString("userId")); // Replace "userId" with the actual claim name
        }

        throw new ApiException(ApiStatus.NOT_FOUND, "User ID not found in JWT token");
    }


}
