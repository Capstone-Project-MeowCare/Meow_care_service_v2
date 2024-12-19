package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.response.AuthenticationResponse;
import com.meow_care.meow_care_service.dto.response.IntrospectResponse;
import com.meow_care.meow_care_service.entities.UserSession;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.UserStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.UserMapper;
import com.meow_care.meow_care_service.repositories.UserRepository;
import com.meow_care.meow_care_service.services.AuthenticationService;
import com.meow_care.meow_care_service.services.UserSessionService;
import com.meow_care.meow_care_service.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final UserSessionService userSessionService;

    private final UserMapper userMapper;

    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(UserRepository userRepository, UserSessionService userSessionService, UserMapper userMapper, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> introspect(IntrospectRequest request) {
        boolean isValid = jwtUtils.isValid(request.getToken());
        return ApiResponse.success(IntrospectResponse.builder().isValid(isValid).build());
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND)
        );

        if (!new BCryptPasswordEncoder().matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ApiStatus.INVALID_CREDENTIALS);
        }

        if (user.getStatus() != UserStatus.ACTIVE ) {
            throw new ApiException(ApiStatus.USER_NOT_ACTIVE);
        }

        String token = jwtUtils.generateToken(user);
        Instant tokenExpiresAt = jwtUtils.tokenExpiryTime().toInstant();
        UserSession userSession = userSessionService.createSession(user, authenticationRequest.getDeviceId(), authenticationRequest.getDeviceName());

        return ApiResponse.success(AuthenticationResponse.builder()
                .token(token)
                .tokenExpiresAt(tokenExpiresAt)
                .refreshToken(userSession.getRefreshToken())
                .refreshTokenExpiresAt(userSession.getExpiresAt())
                .user(userMapper.toDtoWithRole(user))
                .build());
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {

        UserSession userSession = userSessionService.verifyAndRefreshToken(refreshTokenRequest.getToken(), refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getDeviceId());

        String token = jwtUtils.generateToken(userSession.getUser());
        Instant tokenExpiresAt = jwtUtils.tokenExpiryTime().toInstant();

        return ApiResponse.success(AuthenticationResponse.builder()
                .token(token)
                .tokenExpiresAt(tokenExpiresAt)
                .refreshToken(userSession.getRefreshToken())
                .refreshTokenExpiresAt(userSession.getExpiresAt())
                .build());
    }
}
