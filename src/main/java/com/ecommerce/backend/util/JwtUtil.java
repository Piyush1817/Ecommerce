package com.ecommerce.backend.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

//     public static String generateToken(User user) {
//     Map<String, Object> claims = new HashMap<>();
// claims.put("role", user.getRole());
//         return Jwts.builder()
//                 .setSubject(user.getEmail())
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//                 .signWith(key)
//                 .compact();
//     }
private static Key getSigningKey() {
    return Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey".getBytes());
}
public static String generateToken(String email) {
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
            .signWith(getSigningKey())
            .compact();
}

    public static String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
   private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";
   public static Claims extractAllClaims(String token) {
    return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
}
public static String extractRole(String token) {
    return extractAllClaims(token).get("role", String.class);
}
    
}