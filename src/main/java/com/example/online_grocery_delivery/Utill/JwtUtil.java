package com.example.online_grocery_delivery.Utill;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;

    //generate JWT token with username and role
    public String generateToken(String userName, String role) {
        long expirationTime = 1000*60*60;
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    //extract username from token
    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }
    //extract role from token
    public String extractRole(String token){
        return getClaims(token).get("role",String.class);
    }
    //validate token against UserDetail and check expiry
    public boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //check if token expired
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    //Get all claims from token
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
