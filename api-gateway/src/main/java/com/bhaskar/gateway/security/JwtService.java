package com.bhaskar.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (Exception ex) {

            ex.printStackTrace();

            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}