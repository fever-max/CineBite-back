package com.cine.back.user.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UserControllerDocs {

        @Operation(summary = "사용자 정보", description = "사용자 정보를 불러옵니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 정보 불러오기 성공"),
        @ApiResponse(responseCode = "409", description = "사용자 정보 불러오기 실패")})
        public ResponseEntity<?> get();
}
