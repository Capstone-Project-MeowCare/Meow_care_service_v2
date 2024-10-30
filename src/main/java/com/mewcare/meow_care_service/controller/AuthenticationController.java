package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.request.AuthenticationRequest;
import com.mewcare.meow_care_service.dto.request.IntrospectRequest;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.enums.ApiStatus;
import com.mewcare.meow_care_service.services.AuthenticationService;
import com.mewcare.meow_care_service.util.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info("Authenticating user: {}", request.getEmail());
        return authenticationService.authenticate(request);
    }

    @PostMapping("/introspect")
    ResponseEntity<?> authenticate(@RequestBody IntrospectRequest request) {
        return authenticationService.introspect(request);
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    ApiResponse<String> test() {
        log.info("User: {}", UserUtils.getCurrentUserId());
        return ApiResponse.status(ApiStatus.SUCCESS).data("Hello, World!");
    }

//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        var result = authenticationService.refreshToken((RefreshTokenRequest) request);
//        return ApiResponse.<AuthenticationResponse>builder().data(result);
//    }
//
//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder();
//    }
}