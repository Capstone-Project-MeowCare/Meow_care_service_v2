package com.meow_care.meow_care_service.util;

import com.meow_care.meow_care_service.entities.Permission;
import com.meow_care.meow_care_service.entities.Role;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.TokenType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.expiry-hour}")
    private long expiryHour;

    @Value("${jwt.expiry-day}")
    private long expiryDay;

    @Value("${jwt.access-key}")
    private String accessKey;

    @Value("${jwt.refresh-key}")
    private String refreshKey;


    public String generateToken(User user) {
        return generateToken(user, accessKey, tokenExpiryTime(), null);
    }

    public String generateRefreshToken(User user, String id) {
        return generateToken(user, refreshKey, refreshTokenExpiryTime(), id);
    }

    public Date tokenExpiryTime() {
        return new Date(Instant.now().plus(expiryHour, ChronoUnit.HOURS).toEpochMilli());
    }

    public Date refreshTokenExpiryTime() {
        return new Date(Instant.now().plus(expiryDay, ChronoUnit.DAYS).toEpochMilli());
    }

    @SuppressWarnings("unused")
    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting username from token", e);
        }
    }

    //extract token id
    public String extractTokenId(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getJWTID();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting token id from token", e);
        }
    }

    public boolean isValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()) && signedJWT.verify(new MACVerifier(accessKey.getBytes()));
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
    public boolean isValid(String token, TokenType type, User user) {
        try {
            String key = (type == TokenType.ACCESS_TOKEN) ? accessKey : refreshKey;
            SignedJWT signedJWT = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(key.getBytes());

            return signedJWT.verify(verifier) &&
                    signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()) &&
                    user.getEmail().equals(signedJWT.getJWTClaimsSet().getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    private String generateToken(User user, String key, Date expiryTime, String id) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .jwtID(id)
                    .subject(user.getEmail())
                    .issueTime(new Date())
                    .expirationTime(expiryTime)
                    .claim("scope", buildScope(user))
                    .claim("userId", user.getId())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(key.getBytes()));

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    private String buildScope(User user) {
        return user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(java.util.Collection::stream)
                .map(Permission::getPermissionName)
                .collect(Collectors.joining(" "));
    }

}
