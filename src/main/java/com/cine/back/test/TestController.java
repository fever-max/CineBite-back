package com.cine.back.test;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @Tag(name = "User", description = "User 저장 관련 API입니다.")
    @Operation(summary = "유저 정보 저장", description = "유저 정보를 저장합니다.")
    @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공")
    @PostMapping("/test")
    public ResponseEntity<String> testPost(@RequestBody TestEntity userInfo) {
        System.out.println("유저 정보 저장 컨트롤러");
        testService.save(userInfo);
        return ResponseEntity.ok("유저 정보 저장 완료");
    }

    @GetMapping("/test")
    public ResponseEntity<List<TestEntity>> testGet() {
        System.out.println("유저 정보 반환 컨트롤러");
        List<TestEntity> testEntity = testService.findAll();
        return ResponseEntity.ok(testEntity);
    }

}
