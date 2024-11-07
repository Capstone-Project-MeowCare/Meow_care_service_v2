package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.LogoutRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> introspect(IntrospectRequest introspectRequest);

    @SuppressWarnings("unused")
    ResponseEntity<?> outboundAuthenticate(String code);

    ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest);

    @SuppressWarnings("unused")
    ResponseEntity<Void> logout(LogoutRequest logoutRequest);

    @SuppressWarnings("unused")
    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

}
