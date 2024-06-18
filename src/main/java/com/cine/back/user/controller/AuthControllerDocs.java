package com.cine.back.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import com.cine.back.user.dto.request.EmailCertificationRequestDto;
import com.cine.back.user.dto.response.EmailCertificationResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Auth", description = "Auth 관련 API입니다.")
public interface AuthControllerDocs {

        @Operation(summary = "아이디 중복 체크", description = "아이디 중복 여부를 체크합니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "아이디 중복 체크 성공"),
        @ApiResponse(responseCode = "409", description = "아이디 중복 체크 실패(중복된 아이디)")})
        public ResponseEntity<? super IdCheckResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody);

        @Operation(summary = "이메일 인증", description = "이메일 인증 여부를 확인합니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이메일 인증 확인 성공"),
        @ApiResponse(responseCode = "409", description = "이메일 인증 확인 실패(중복된 아이디)")})
        public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody);
}
