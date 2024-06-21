package com.cine.back.user.provider;

import java.util.Date;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private Key key;

    public JwtProvider(@Value("${spring.jwt.secret}")String secret) {

		byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    // 생성
    public String create(String userId, String userRole, Long expiredMs) {

		Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("userRole", userRole);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userId", String.class);
    }

    public String getUserRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userRole", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    // 검증
    public String validate(String jwt) {

        String subject = null;

        try {
            subject = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        } catch (Exception e) {
            
            e.printStackTrace();
            return null;
        }

        return subject;
    }
}
