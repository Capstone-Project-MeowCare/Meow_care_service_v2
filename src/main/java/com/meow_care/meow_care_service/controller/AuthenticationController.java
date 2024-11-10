package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.UserWithRoleDto;
import com.meow_care.meow_care_service.dto.request.AuthenticationRequest;
import com.meow_care.meow_care_service.dto.request.IntrospectRequest;
import com.meow_care.meow_care_service.dto.request.RefreshTokenRequest;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.AuthenticationService;
import com.meow_care.meow_care_service.services.UserService;
import com.meow_care.meow_care_service.util.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

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

    //get info from token
    @GetMapping("/info")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> info() {
        return userService.get(UserUtils.getCurrentUserId());
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('TEST')")
    ApiResponse<UserWithRoleDto> test() {
        log.info("User: {}", UserUtils.getCurrentUserId());
        return userService.getUserWithRoles(UserUtils.getCurrentUserId());
    }

    //refresh token
    @PostMapping("/refresh")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return authenticationService.refreshToken(request);
    }

}