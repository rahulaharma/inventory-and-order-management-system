package org.example.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();
        // Add the user's role as a custom claim. This is good practice.
        claims.put("role", user.getRole().getName());
        claims.put("userId", user.getId());

        // Use the modern, correct builder syntax
        return Jwts.builder()
                .claims(claims) // Set all custom claims at once
                .subject(user.getUsername()) // Use the standard getUsername()
                .issuer("RAHUL")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(generateKey())
                .compact();
    }

    public SecretKey generateKey(){
        byte[] decode= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token , Function<Claims,T> claimsResolver){
        Claims claims=extractClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractClaims(String token){
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }
    public boolean isTokenValid(String token) {
        try{
            final Date expiration=extractClaims(token,Claims::getExpiration);
            return expiration.after(new Date());
        }
        catch (Exception e) {
            return false;
        }
    }
}

