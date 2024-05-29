package com.cine.back.test;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public void save(TestEntity userInfo) {
        testRepository.save(userInfo);
    }

    public List<TestEntity> findAll() {
        List<TestEntity> testEntity = testRepository.findAll();
        return testEntity;
    }

}
