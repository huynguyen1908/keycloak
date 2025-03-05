package com.example.CORStest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    private String SECRET_KEY = "LijiNebuza1W1Ot6QEfwqov22mI7mzTJRVCqFEiwKK8Y4POPPiR0uVisVafv3Vi1";

    // Lấy username từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Lấy expiration từ token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Lấy thông tin claim từ token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes()) // Dùng getBytes()
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // Kiểm tra token đã hết hạn chưa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Tạo token từ username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token có hiệu lực trong 10 giờ
//              .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Xác minh token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

//    public String extractRole(String token) {
//        return extractClaim(token, claims -> claims.get("role", String.class));
//    }
}
