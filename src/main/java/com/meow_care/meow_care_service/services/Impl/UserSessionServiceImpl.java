package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.entities.UserSession;
import com.meow_care.meow_care_service.repositories.UserSessionRepository;
import com.meow_care.meow_care_service.services.UserSessionService;
import com.meow_care.meow_care_service.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@AllArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public UserSession createSession(User user, String deviceId, String deviceName) {

        String refreshToken = JwtUtils.generateRefreshToken(user);
        Instant issuedAt = Instant.now();
        Instant refreshTokenExpiresAt = JwtUtils.refreshTokenExpiryTime().toInstant();

        UserSession userSession = UserSession.builder()
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
}
