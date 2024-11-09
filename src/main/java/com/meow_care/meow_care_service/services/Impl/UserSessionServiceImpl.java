package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.entities.UserSession;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.repositories.UserSessionRepository;
import com.meow_care.meow_care_service.services.UserService;
import com.meow_care.meow_care_service.services.UserSessionService;
import com.meow_care.meow_care_service.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepository;

    private final UserService userService;

    @Override
    public UserSession createSession(User user, String deviceId, String deviceName) {

        UUID tokenId = UUID.randomUUID();
        String refreshToken = JwtUtils.generateRefreshToken(user, tokenId.toString());
        Instant issuedAt = Instant.now();
        Instant refreshTokenExpiresAt = JwtUtils.refreshTokenExpiryTime().toInstant();

        UserSession userSession = UserSession.builder()
                .id(tokenId)
                .user(user)
                .deviceId(deviceId)
                .refreshToken(refreshToken)
                .issuedAt(issuedAt)
                .expiresAt(refreshTokenExpiresAt)
                .deviceName(deviceName)
                .build();
        log.info(userSession.toString());
        return userSessionRepository.save(userSession);
    }

    //verify the token and return new token
    @Override
    public UserSession verifyAndRefreshToken(String accessToken, String refreshToken, String deviceId) {
        String refreshTokenId  = JwtUtils.extractTokenId(refreshToken);
        UserSession userSession = userSessionRepository.findById(UUID.fromString(refreshTokenId ))
                .orElseThrow(() -> new ApiException(ApiStatus.TOKEN_NOT_VALID ,"Invalid refresh token"));

        if (userSession.getExpiresAt().isBefore(Instant.now())) {
            throw new ApiException(ApiStatus.TOKEN_NOT_VALID, "Refresh token expired");
        }

        if (!JwtUtils.extractUsername(accessToken).equals(JwtUtils.extractUsername(refreshToken)) ||
                !userSession.getDeviceId().equals(deviceId) ||
                !refreshToken.equals(userSession.getRefreshToken())) {
            throw new ApiException(ApiStatus.TOKEN_NOT_VALID, "Invalid refresh token");
        }

        User user = userService.findEntityById(userSession.getUser().getId())
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "User not found"));

        String newRefreshToken = JwtUtils.generateRefreshToken(user, refreshTokenId);
        userSession.setRefreshToken(newRefreshToken);
        userSession.setIssuedAt(Instant.now());
        userSession.setExpiresAt(JwtUtils.refreshTokenExpiryTime().toInstant());

        return userSessionRepository.save(userSession);
    }
}
