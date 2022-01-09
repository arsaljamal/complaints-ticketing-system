package com.arsal.ticketingservice.security;

import com.arsal.ticketingservice.domain.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtConfiguration {

    private final String rolesKey;

    private final String secretKey;

    private final long expiryInMilliseconds;

    public JwtConfiguration(@Value("${jwt.roles.key}") String rolesKey,
                            @Value("${jwt.token.secret-key}")String secretKey,
                            @Value("${jwt.token.expiration}")long expiryInMilliseconds) {
        this.rolesKey = rolesKey;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());;
        this.expiryInMilliseconds = expiryInMilliseconds;
    }

    public String createToken(String username, List<Roles> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(rolesKey, roles.stream().map(role ->new SimpleGrantedAuthority(role.getAuthority()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expiryInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

}
