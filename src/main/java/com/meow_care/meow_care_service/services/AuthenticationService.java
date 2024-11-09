package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.LogoutRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    ResponseEntity<?> introspect(IntrospectRequest introspectRequest);

    ResponseEntity<?> outboundAuthenticate(String code);

    ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest);

    ResponseEntity<Void> logout(LogoutRequest logoutRequest);

    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

}
