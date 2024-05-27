package com.cine.back.test;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public void save(TestEntity userInfo) {
        System.out.println("유저 정보 저장 서비스");
        testRepository.save(userInfo);
    }

    public List<TestEntity> findAll() {
        System.out.println("유저 정보 반환 서비스");
        List<TestEntity> testEntity = testRepository.findAll();
        return testEntity;
    }

}
