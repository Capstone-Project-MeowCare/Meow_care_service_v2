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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


public class JwtUtils {

    private static final long expiryHour = 1;

    private static final long expiryDay = 1;

    private static final String accessKey = "3f5e8b7a9c1d2e3f4a5b6c7d8e9f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f";

    private static final String refreshKey = "3f5e8b7a9c1d2e3f4a5b6c7d8e9f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f";


    public static String generateToken(User user) {
        return generateToken(user, accessKey, tokenExpiryTime());
    }

    public static String generateRefreshToken(User user) {
        return generateToken(user, refreshKey, refreshTokenExpiryTime());
    }

    public static Date tokenExpiryTime() {
        return new Date(Instant.now().plus(expiryHour, ChronoUnit.HOURS).toEpochMilli());
    }

    public static Date refreshTokenExpiryTime() {
        return new Date(Instant.now().plus(expiryDay, ChronoUnit.DAYS).toEpochMilli());
    }

    @SuppressWarnings("unused")
    public static String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting username from token", e);
        }
    }

    public static boolean isValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date()) && signedJWT.verify(new MACVerifier(accessKey.getBytes()));
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
    public static boolean isValid(String token, TokenType type, User user) {
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

    private static String generateToken(User user, String key, Date expiryTime) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .jwtID(UUID.randomUUID().toString())
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

    private static String buildScope(User user) {
        return user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(java.util.Collection::stream)
                .map(Permission::getPermissionName)
                .collect(Collectors.joining(" "));
    }

}
