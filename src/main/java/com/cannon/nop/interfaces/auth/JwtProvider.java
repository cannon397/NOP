package com.cannon.nop.interfaces.auth;

import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
@Slf4j
public class JwtProvider {

    private final SecretKey secretKey;
    public String generateToken(String eventUrlUUID) {
        return Jwts.builder()
                .subject(eventUrlUUID)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분동안 유효
                .signWith(secretKey)
                .compact();
    }

    public JwtProvider(@Value("${jwt.secret-key}") String key) {

        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractClaims(String token) throws ApiException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new ApiException(ErrorCode.ACCESS_TOKEN_ERROR);
        }
        return claims;
    }

    public String extractEventUrlUUID(String token) throws ApiException {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) throws ApiException {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
