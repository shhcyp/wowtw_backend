package cn.wowtw_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String SECRET_KEY = "gtORUQljR76IC6MzysLBR8hZOZMCqswGF0jymCRkixE="; // 确保这里的密钥与验证时使用的密钥一致
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final Date expirationDate = new Date(System.currentTimeMillis() + 43200000L);

    // 生成 JWT
    public static String generateJwt(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username) // Subject 可以根据实际需要设置
                .signWith(key)
                .setExpiration(expirationDate)
                .compact();
    }

    // 解析 JWT
    public static Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}