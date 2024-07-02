package com.cine.back.user.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReissueControllerDocs {

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
    @ApiResponse(responseCode = "409", description = "토큰 재발급 실패")})
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}
