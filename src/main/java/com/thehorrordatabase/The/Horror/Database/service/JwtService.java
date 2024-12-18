package com.thehorrordatabase.The.Horror.Database.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken(UserDetails userDetails, Long userId) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Ajoute le username comme "sub"
                .claim("roles", userDetails.getAuthorities()) // Ajoute les rôles
                .claim("userId", userId) // Ajoute l'ID utilisateur
                .setIssuedAt(Date.from(Instant.now())) // Date d'émission
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Date d'expiration
                .signWith(SignatureAlgorithm.HS256, secretKey) // Signature avec la clé secrète
                .compact();
    }



    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}