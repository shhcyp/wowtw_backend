package cn.wowtw_backend.filter;

import cn.wowtw_backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            log.debug("JWT Token: {}", jwt);
            Claims claims = JwtUtils.parseJwt(jwt);

            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("Claims: {}", claims);
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.debug("Authentication successful for user: {}", claims.getSubject());
            } else {
                log.warn("Claims is null or context already contains authentication");
            }
        } else {
            log.warn("Authorization header is missing or does not start with 'Bearer '");
        }

        filterChain.doFilter(request, response);
    }
}