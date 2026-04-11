package com.ecommerce.backend.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static SecretKey getKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
}

    // ✅ Generate Token
    public static String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    // ✅ Extract Email
    public static String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract Role
    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ Extract Claims
      private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())   // ✅ now works after chnaging  SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}