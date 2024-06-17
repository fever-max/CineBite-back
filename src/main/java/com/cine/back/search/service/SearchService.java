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

        log.info("검색어 저장 서비스");
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

           // 검색어를 SearchEntity로 변환하여 저장
           saveSearchEntities(userId, keywords);

            log.info("검색어 저장 서비스 - 성공");

        } catch (Exception e) {
            log.error("검색어 저장 서비스 - 실패", e);
            throw new RuntimeException("검색어 서비스 - 저장 중 오류 발생");
        }
    }

    //사용자 ID와 검색 리스트 받아서 SearchEntity로 변환하고 저장
    @Transactional
    private void saveSearchEntities(String userId, List<String> keywords) {
        List<SearchEntity> searchEntities = new ArrayList<>();
        for (String keyword : keywords) {
            SearchEntity searchEntity = new SearchEntity();
            searchEntity.setUserId(userId);
            searchEntity.setSearchKeyword(keyword);
            searchEntity.setSearchListTime(LocalDateTime.now());
            searchEntities.add(searchEntity);
        }

        // 검색어 엔티티를 저장
        for (SearchEntity searchEntity : searchEntities) {
            searchRepository.save(searchEntity);
        }
    }

    // 사용자 ID에 따른 검색어 리스트 조회
    @Transactional(readOnly = true)
    public List<SearchEntity> getSearchListByUserId(String userId) {

        if (userId == null) {
            log.info("검색어 조회 서비스 - 모든 사용자 검색어 조회");
            return searchRepository.findAll();
        } else {
            log.info("검색어 조회 서비스 - 사용자 {}의 검색어 조회", userId);
            return searchRepository.findByUserIdOrderBySearchListTimeDesc(userId);
        }
    }
    
}
