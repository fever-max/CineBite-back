package com.cine.back.search.service;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final RelatedService relatedService;

    // 검색어 저장
    @Transactional
    public void saveSearchList(SearchRequest request) {
        log.info("검색어 저장 서비스 시작");
        String userId = checkUserId(request.userId());
        List<String> keywords = request.keywords();
        if (keywords == null || keywords.isEmpty()) {
            log.warn("검색어 저장 서비스 - 검색어가 없습니다.");
            return;
        }
        for (String keyword : keywords) {
            saveSearchEntity(userId, keyword);
        }
        log.info("검색어 저장 서비스 완료");
    }

    // 사용자ID 확인
    private String checkUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            userId = "guest";
            log.info("검색어 저장 서비스 - 사용자 ID가 없으므로 'guest'로 설정합니다.");
        }
        return userId;
    }

    // SearchEntity 저장
    @Transactional
    private void saveSearchEntity(String userId, String keyword) {
        SearchEntity searchEntity = createSearchEntity(userId, keyword);
        searchEntity = searchRepository.save(searchEntity);
        relatedService.saveRelatedEntity(searchEntity, keyword);
    }

    private SearchEntity createSearchEntity(String userId, String keyword) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setUserId(userId);
        searchEntity.setSearchKeyword(keyword);
        searchEntity.setSearchListTime(LocalDateTime.now());
        return searchEntity;
    }

    // 로그인 한 사용자 최근 검색 리스트 불러오기
    @Transactional(readOnly = true)
    public List<SearchEntity> getSearchListByUserId(String userId) {
        if (userId == null) {
            return searchRepository.findAll();
        }
        return searchRepository.findByUserIdOrderBySearchListTimeDesc(userId);
    }

    // 검색어 삭제
    @Transactional
    public void deleteSearchKeyword(String userId, int searchListNo) {
        SearchEntity deleteData = searchRepository.findByUserIdAndSearchListNo(userId, searchListNo);
        if (deleteData == null) {
            throw new IllegalArgumentException("검색어 삭제 서비스 - 검색어가 없습니다.");
        }
        searchRepository.delete(deleteData);
    }

    // 검색어 전체삭제
    @Transactional
    public void deleteAllSearchKeyword(String userId) {
        try {
            searchRepository.deleteByUserId(userId);
            log.info("사용자 {} 검색어 전체삭제 서비스 - 완료", userId);
        } catch (Exception e) {
            log.error("사용자 {} 검색어 전체삭제 서비스 - 실패", userId, e);
            throw new RuntimeException("사용자 검색어 전체 삭제 실패", e);
        }
    }
}
