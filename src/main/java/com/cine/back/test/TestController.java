package com.cine.back.test;

import org.springframework.web.bind.annotation.RestController;

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
