package com.cine.back.user.controller;

import com.cine.back.user.service.AuthService;
import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import com.cine.back.user.dto.response.ResponseDto;
import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.request.CheckCertificationRequestDto;
import com.cine.back.user.dto.request.EmailCertificationRequestDto;
import com.cine.back.user.dto.response.CheckCertificationResponseDto;
import com.cine.back.user.dto.response.EmailCertificationResponseDto;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDocs{
    
    private final AuthService authService;

    // 아이디 중복 체크
    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck (@RequestBody @Valid IdCheckRequestDto requestBody) {

        log.info("아이디 중복 체크 컨트롤러 실행");

        ResponseEntity<? super IdCheckResponseDto> response = authService.userIdCheck(requestBody);
        return response;
    }

    // 이메일 중복 확인
    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCertificationResponseDto> checkEmail(@RequestBody @Valid EmailCertificationRequestDto requestBody) {

        log.info("이메일 중복 체크 컨트롤러 실행");
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.checkEmail(requestBody);
        return response;

    }

    // 이메일 인증
    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {

        log.info("이메일 인증 컨트롤러 실행");
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.emailCertification(requestBody);
        return response;
    }

    // 인증번호 확인
    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) {

        log.info("이메일 인증 확인 컨트롤러 실행");
        ResponseEntity<? super CheckCertificationResponseDto> response = authService.checkCertification(requestBody);
        return response;
    }
    
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<? super ResponseDto> join(@RequestBody UserDTO userDto, BindingResult bindingResult){

        log.info("회원가입 컨트롤러 실행");
        if (bindingResult.hasErrors()) { // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return authService.join(userDto);
    }

    // 아이디 찾기
    @PostMapping("/findUserId")
    public ResponseEntity<?> findUserId(@RequestBody EmailCertificationRequestDto request) {

        log.info("아이디찾기 컨트롤러 실행");
        return authService.findUserId(request.getUserEmail());
    }

    // 비밀번호 초기화
    @PostMapping("/findUserPwd")
    public ResponseEntity<?> findUserPwd(@RequestBody EmailCertificationRequestDto request) {

        log.info("비밀번호 초기화 컨트롤러 실행");
        return authService.resetUserPwd(request.getUserEmail(), request.getUserId());
    }
}
