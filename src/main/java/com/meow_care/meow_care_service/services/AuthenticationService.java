package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> introspect(IntrospectRequest introspectRequest);

    ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest);

    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

}
