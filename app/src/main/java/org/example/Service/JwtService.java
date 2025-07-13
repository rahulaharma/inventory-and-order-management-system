package org.example.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.model.User;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private String secretKey=null;

    public String generateToken(User user) {
        Map<String,Object> claims=new HashMap<>();
        return Jwts.
                builder().
                claims().
                add(claims).
                subject(user.getUserName()).
                issuer("RAHUL").
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+10*60*10000)).
                and().
                signWith(generateKey()).compact();
    }

    public SecretKey generateKey(){
        byte[] decode= Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }
    public String getSecretKey(){
        return secretKey="Pq0dh0M/uICdFVyxeEAGj2vet4EVioDbaH8tfOt+R1QVUM3kE8EUR4lYoan7VISM"; // base64 string will be converted to secret key
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

