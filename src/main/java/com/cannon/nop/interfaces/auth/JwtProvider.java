package com.cannon.nop.interfaces.auth;

import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build(); // 비밀 키

    public String generateToken(String eventUrlUUID) {
        return Jwts.builder()
                .subject(eventUrlUUID)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분동안 유효
                .signWith(secretKey)
                .compact();
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
