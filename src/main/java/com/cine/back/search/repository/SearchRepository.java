package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.SearchEntity;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Integer> {

    // 사용자ID로 검색 (날짜 내림차순)
    List<SearchEntity> findByUserIdOrderBySearchListTimeDesc(String userId);

    // 주어진 검색어로 검색 엔터티를 찾습니다.
    SearchEntity findBySearchKeyword(String searchKeyword);

    // 삭제 - 사용자Id와 검색어번호로 조회
    SearchEntity findByUserIdAndSearchListNo(String userId, int searchListNo);

    // 전체삭제
    void deleteByUserId(String userId);

}
