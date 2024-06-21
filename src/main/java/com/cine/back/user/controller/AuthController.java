package com.cine.back.user.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.user.service.AuthService;
import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs{
    
    private final AuthService authService;

    // 아이디 중복 체크
    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck (@RequestBody @Valid IdCheckRequestDto requestBody) {

        log.info("아이디 중복 체크 컨트롤러 실행");

        ResponseEntity<? super IdCheckResponseDto> response = authService.userIdCheck(requestBody);
        return response;
    }
}
