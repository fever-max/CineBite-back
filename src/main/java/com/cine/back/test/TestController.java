package com.cine.back.test;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController implements TestControllerDocs {

    private final TestService testService;

    @PostMapping("/save")
    public ResponseEntity<String> testPost(@RequestBody TestEntity userInfo) {
        System.out.println("유저 정보 저장 컨트롤러");
        try {
            testService.save(userInfo);
            return ResponseEntity.ok("유저 정보 저장 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("유저 정보 저장 실패");
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TestEntity>> testGet() {
        System.out.println("유저 정보 반환 컨트롤러");

        try {
            List<TestEntity> testEntity = testService.findAll();
            return ResponseEntity.ok(testEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

}
