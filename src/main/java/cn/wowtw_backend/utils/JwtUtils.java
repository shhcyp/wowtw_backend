package cn.wowtw_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String subject = "wowtw";
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Date expirationDate = new Date(System.currentTimeMillis() + 43200000L);

    // 生成jwt
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .signWith(key)
                .setExpiration(expirationDate)
                .compact();
    }

    // 解析jwt
    public static Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
