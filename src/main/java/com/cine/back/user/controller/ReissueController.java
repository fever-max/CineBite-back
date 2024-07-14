package com.cine.back.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cine.back.user.entity.RefreshEntity;
import com.cine.back.user.provider.JwtProvider;
import com.cine.back.user.repository.RefreshRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
public class ReissueController implements ReissueControllerDocs {

    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;

    public ReissueController(JwtProvider jwtProvider, RefreshRepository refreshRepository) {
        this.jwtProvider = jwtProvider;
        this.refreshRepository = refreshRepository;
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    @Transactional
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Reissue started");

        // refresh token 확인
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        } else {
            log.debug("No cookies");
            return new ResponseEntity<>("no cookies", HttpStatus.BAD_REQUEST);
        }
        if (refresh == null) {
            log.debug("refresh null");
            return new ResponseEntity<>("refresh null", HttpStatus.BAD_REQUEST);
        }

        // 토큰 만료 확인
        try {
            jwtProvider.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.debug("refresh expired");
            return new ResponseEntity<>("refresh expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰 카테고리 확인
        String category = jwtProvider.getCategory(refresh);

        if (!"refresh".equals(category)) {
            log.debug("Invalid refresh category");
            return new ResponseEntity<>("invalid refresh category", HttpStatus.BAD_REQUEST);
        }

        // DB 존재여부 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            log.debug("Invalid refresh database");
            return new ResponseEntity<>("invalid refresh database", HttpStatus.BAD_REQUEST);
        }

        String userId = jwtProvider.getUserId(refresh);
        String userNick = jwtProvider.getUserNick(refresh);
        String userRole = jwtProvider.getUserRole(refresh);

        String newAccess = jwtProvider.create("access", userId, userNick, userRole, 30 * 60 * 1000L);
        String newRefresh = jwtProvider.create("refresh", userId, userNick, userRole, 24 * 60 * 60 * 1000L);

        // DB에 기존 토큰 삭제 후 새 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(userId, newRefresh, 24 * 60 * 60 * 1000L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        log.debug("reissue success: {}", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addRefreshEntity(String userId, String refresh, Long expiredMs) {
        List<RefreshEntity> existingTokens = refreshRepository.findByUserId(userId);
        if (!existingTokens.isEmpty()) {
            refreshRepository.deleteAll(existingTokens);
        }
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUserId(userId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date);
        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        log.debug("Cookie created: {}={}", key, value);
        return cookie;
    }
}
