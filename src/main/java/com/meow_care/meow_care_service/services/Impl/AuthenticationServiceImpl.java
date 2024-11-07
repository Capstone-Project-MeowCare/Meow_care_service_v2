package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.LogoutRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.response.AuthenticationResponse;
import com.meow_care.meow_care_service.dto.response.IntrospectResponse;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.UserMapper;
import com.meow_care.meow_care_service.repositories.UserRepository;
import com.meow_care.meow_care_service.services.AuthenticationService;
import com.meow_care.meow_care_service.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> introspect(IntrospectRequest request) {
        boolean isValid = JwtUtils.isValid(request.getToken());
        return ApiResponse.success(IntrospectResponse.builder().isValid(isValid).build());
    }

    @Override
    public ResponseEntity<?> outboundAuthenticate(String code) {
        return null;
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND)
        );

        if (!new BCryptPasswordEncoder().matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ApiStatus.UNAUTHORIZED);
        }

        String token = JwtUtils.generateToken(user);
        String refreshToken = JwtUtils.generateRefreshToken(user);

        return ApiResponse.success(AuthenticationResponse.builder()
                .token(token)
                .tokenExpiresAt(JwtUtils.tokenExpiryTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresAt(JwtUtils.refreshTokenExpiryTime())
                .user(userMapper.toDtoWithRole(user))
                .build());
    }

    @Override
    public ResponseEntity<Void> logout(LogoutRequest logoutRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }
}
