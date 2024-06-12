package com.cine.back.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.entity.CustomOAuth2User;
import com.cine.back.user.provider.JwtProvider;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JWTFilter(JwtProvider jwtProvider) {

        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /* 헤더에서 토큰
        String authorization= request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }
			
        System.out.println("authorization now");
        String token = authorization.split(" ")[1];
        */
        
        // 쿠키
        // cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            System.out.println(cookie.getName());
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }

        if (authorization == null) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization;
        
        if (jwtProvider.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String userId = jwtProvider.getUserId(token);
        String userRole = jwtProvider.getUserRole(token);
        
        /* 헤더
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");
        userEntity.setRole(role);
        
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        */

        // 쿠키
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserRole(userRole);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
