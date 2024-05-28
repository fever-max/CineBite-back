package com.cine.back.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Test", description = "Test 관련 API입니다.")
public interface TestControllerDocs {

    @Operation(summary = "유저 정보 저장", description = "유저 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
            @ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public ResponseEntity<String> testPost(@RequestBody TestEntity userInfo);

    @Operation(summary = "전체 유저 정보 반환", description = "전체 유저 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 전체 정보 반환 성공"),
            @ApiResponse(responseCode = "400", description = "유저 전체 정보 반환 실패") })
    public ResponseEntity<List<TestEntity>> testGet();

}
