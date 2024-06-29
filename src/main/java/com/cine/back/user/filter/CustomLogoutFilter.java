package com.cine.back.user.filter;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import com.cine.back.user.provider.JwtProvider;
import com.cine.back.user.repository.RefreshRepository;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JwtProvider jwtProvider, RefreshRepository refreshRepository) {
        this.jwtProvider = jwtProvider;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUri = httpRequest.getRequestURI(); // 로그아웃 요청이 아닌 경우
        if (!requestUri.matches("^\\/logout$")) {
            chain.doFilter(request, response);
            return;
        }
        String requestMethod = httpRequest.getMethod(); // 로그아웃 POST 요청(refresh token 전달)
        if (!requestMethod.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // refresh token 확인
        String refresh = null; 
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }
        if (refresh == null) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰 만료 확인
        try {
            jwtProvider.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰 카테고리 확인
        String category = jwtProvider.getCategory(refresh);
        if (!"refresh".equals(category)) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // DB 존재여부 확인
        boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        httpResponse.addCookie(cookie);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}