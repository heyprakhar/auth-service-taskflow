package com.authentication.taskflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /*
     * GENERATE ACCESS TOKEN
     */

    public String generateAccessToken(String email, String role) {

        Map<String, Object> claims = new HashMap<>();

        if (role != null) {
            claims.put("role", role);
        }

        return generateToken(
                claims,
                email,
                accessExpiration);
    }

    /*
     * GENERATE REFRESH TOKEN
     */

    public String generateRefreshToken(String email) {

        Map<String, Object> claims = new HashMap<>();

        return generateToken(
                claims,
                email,
                refreshExpiration);
    }

    /*
     * GENERIC TOKEN BUILDER
     */

    private String generateToken(
            Map<String, Object> claims,
            String subject,
            long expiration) {

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(signingKey)
                .compact();
    }

    /*
     * EXTRACT EMAIL
     */

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * EXTRACT ROLE
     */

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /*
     * EXTRACT EXPIRATION
     */

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * GENERIC CLAIM EXTRACTOR
     */

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /*
     * PARSE TOKEN
     */

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     * CHECK TOKEN VALIDITY
     */

    public boolean isTokenValid(String token, String email) {

        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    /*
     * CHECK TOKEN EXPIRY
     */

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}