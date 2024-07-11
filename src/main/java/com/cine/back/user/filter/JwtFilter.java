package com.cine.back.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.oauth2.CustomUserDetails;
import com.cine.back.user.provider.JwtProvider;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        log.debug("Request URI: {}", requestUri);
        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("access");
        log.debug("Access token: {}", accessToken);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
    
        // 토큰 만료 확인
        try {
            jwtProvider.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            log.info("access token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인
        String category = jwtProvider.getCategory(accessToken);

        // request header에서 access로 온 토큰이 진짜 access 인지 확인
        if (!category.equals("access")) {
            log.info("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // userId, userRole 값 획득
        String userId = jwtProvider.getUserId(accessToken);
        String userRole = jwtProvider.getUserRole(accessToken);

        UserDTO userDto = UserDTO.builder()
                .userName(userId)
                .userRole(userRole)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(userDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}