package com.mewcare.meow_care_service.configurations;

import com.mewcare.meow_care_service.dto.request.IntrospectRequest;
import com.mewcare.meow_care_service.dto.response.IntrospectResponse;
import com.mewcare.meow_care_service.services.AuthenticationService;
import com.mewcare.meow_care_service.util.JwtUtils;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;


@Slf4j
@Component
public class CustomJwtDecoder implements JwtDecoder {

    private String accessKey = "3f5e8b7a9c1d2e3f4a5b6c7d8e9f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f";

    private AuthenticationService authenticationService;

    private NimbusJwtDecoder jwtDecoder = null;

    public CustomJwtDecoder(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            if (!JwtUtils.isValid(token)) throw new RuntimeException("Invalid token");
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }

        try {
            if (Objects.isNull(jwtDecoder)) {
                SecretKeySpec key = new SecretKeySpec(this.accessKey.getBytes(), "HmacSHA512");
                jwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
            return jwtDecoder.decode(token);

        } catch (Exception e) {
            log.error("Error during token introspection", e);
            throw new RuntimeException("Invalid token", e);
        }
    }
}
