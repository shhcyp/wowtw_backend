package cn.wowtw_backend.filter;

import cn.wowtw_backend.model.user.User;
import cn.wowtw_backend.service.UserService;
import cn.wowtw_backend.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        // log.info("authorizationHeader: {}", authorizationHeader);
        String sessionIdHeader = request.getHeader("X-Session-Id");
        // log.info("sessionIdHeader:{}", sessionIdHeader);

        // 不需要验证 Authorization 的路径
        String requestURI = request.getRequestURI();
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            // log.info("JWT Token: {}", jwt);
            Claims claims = JwtUtils.parseJwt(jwt);

            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // log.info("Claims: {}", claims);

                String username = claims.getSubject();
                String sessionId = claims.get("sessionId", String.class);

                if (username != null && sessionId != null) {
                    User user = userService.getByUsername(username);
                    // log.info("user: {}", user.getUsername());

                    if (user != null && sessionId.equals(user.getSessionId()) && user.getSessionActive()) {
                        // log.info("(user.getSessionId() {}", user.getSessionId());
                        // log.info("user.getSessionActive() {}", user.getSessionActive());
                        // 验证 X-Session-Id 头
                        if (sessionIdHeader != null && sessionIdHeader.equals(sessionId)) {
                            // 会话有效
                            var authenticationToken = new UsernamePasswordAuthenticationToken(
                                    username, null, Collections.emptyList());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            log.debug("Authentication successful for user: {}", username);
                        } else {
                            // sessionId 不匹配或无效
                            // log.warn("Session ID does not match or session is inactive for user: {}", username);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"code\": 401, \"message\": \"Session invalid\"}");

                            return;
                        }
                    } else {
                        // 会话无效
                        // log.warn("Session ID does not match or session is inactive for user: {}", username);
                        response.getWriter().write("{\"code\": 401, \"message\": \"Session invalid\"}");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                        return;
                    }
                } else {
                    log.warn("Username or session ID is missing in claims");
                }
            } else {
                log.warn("Claims is null or context already contains authentication");
            }
        } else {
            log.warn("Authorization header is missing or does not start with 'Bearer '");
        }

        filterChain.doFilter(request, response);
    }
}