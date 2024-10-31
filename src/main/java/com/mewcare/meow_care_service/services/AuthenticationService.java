package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.request.AuthenticationRequest;
import com.mewcare.meow_care_service.dto.request.IntrospectRequest;
import com.mewcare.meow_care_service.dto.request.LogoutRequest;
import com.mewcare.meow_care_service.dto.request.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> introspect(IntrospectRequest introspectRequest);

    ResponseEntity<?> outboundAuthenticate(String code);

    ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest);

    ResponseEntity<Void> logout(LogoutRequest logoutRequest);

    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

}
