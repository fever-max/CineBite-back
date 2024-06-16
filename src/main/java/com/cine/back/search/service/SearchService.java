package com.cine.back.search.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    // 사용자Id로 SearchEntity를 생성해서 검색어 저장
    @Transactional
    public void saveSearchList(SearchRequest request) {
        try {
            String userId = request.userId();
            List<String> keywords = request.keywords();

            if (keywords == null) {
                keywords = new ArrayList<>();
                log.warn("검색어가 없습니다. 빈 리스트로 초기화합니다.");
            }

            if (userId == null || userId.isEmpty()) {
                userId = "guest"; // 사용자 ID가 없는 경우 "게스트"로 지정
                log.info("사용자 ID가 없으므로 'guest'를 사용합니다.");
            }

            // 사용자 ID와 keyword로 SearchEntity를 생성해서 리스트에 추가
            List<SearchEntity> searchEntities = new ArrayList<>();
            for (String keyword : keywords) {
                SearchEntity searchEntity = new SearchEntity();
                searchEntity.setUserId(userId);
                searchEntity.setSearchKeyword(keyword);
                searchEntity.setSearchListTime(LocalDateTime.now());
                searchEntities.add(searchEntity);
            }

            for (SearchEntity searchEntity : searchEntities) {
                searchRepository.save(searchEntity);
            }

            log.info("검색어 저장 서비스 - 저장 성공");

        } catch (Exception e) {
            log.error("검색어 저장 서비스 - 저장 실패", e);
            throw new RuntimeException("검색어 저장 서비스 - 저장 중 오류 발생");
        }
    }

    @Transactional(readOnly = true)
    // 사용자 ID에 따른 검색어 리스트를 조회합니다.

    public List<SearchEntity> getSearchListByUserId(String userId) {
        if (userId == null) {
            log.info("모든 사용자의 검색어 조회");
            return searchRepository.findAll();
        } else {
            log.info("사용자 {}의 검색어 조회", userId);
            return searchRepository.findByUserId(userId);
        }
    }
}
