package com.meow_care.meow_care_service.configurations;

import com.meow_care.meow_care_service.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.access-key}")
    private String accessKey;

    private NimbusJwtDecoder jwtDecoder = null;

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
