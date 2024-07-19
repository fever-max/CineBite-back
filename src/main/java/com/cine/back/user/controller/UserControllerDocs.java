package com.cine.back.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.request.PasswordValidationRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UserControllerDocs {

        @Operation(summary = "사용자 정보", description = "사용자 정보를 불러옵니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 정보 불러오기 성공"),
        @ApiResponse(responseCode = "409", description = "사용자 정보 불러오기 실패")})
        public ResponseEntity<?> get();

        @Operation(summary = "비밀번호 확인", description = "비밀번호를 확인합니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "비밀번호 확인 성공"),
        @ApiResponse(responseCode = "409", description = "비밀번호 확인 실패")})
        public ResponseEntity<Boolean> validatePassword(PasswordValidationRequest request);

        @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공"),
        @ApiResponse(responseCode = "409", description = "사용자 정보 수정 실패")})
        public ResponseEntity<?> updateUser( UserDTO user);

        @Operation(summary = "프로필 이미지 변경", description = "프로필 이미지를 변경합니다.")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "프로필 이미지 변경 성공"),
        @ApiResponse(responseCode = "409", description = "프로필 이미지 변경 실패")})
        public ResponseEntity<?> modify(@ModelAttribute UserDTO userDTO);
}
