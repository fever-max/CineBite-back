package com.cine.back.search.service;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final RelatedService relatedService;

    @Transactional
    public int saveSearchList(SearchRequest request) {
        log.info("검색어 저장 서비스 시작");

        String userId = checkUserId(request.userId());
        List<String> keywords = request.keywords();

        int searchListNo = 0;
        int previousSearchListNo = request.searchListNo();

        for (String keyword : keywords) {
            Optional<SearchEntity> existingEntity = searchRepository.findByUserIdAndSearchKeyword(
                    userId,
                    keyword);
            if (existingEntity.isPresent()) {
                log.info("이미 저장된 검색어입니다: {}", keyword);
                continue;
            }
            SearchEntity searchEntity = saveSearchEntity(userId, keyword);
            if (searchListNo == 0) {
                searchListNo = searchEntity.getSearchListNo();
                log.info("검색어를 저장했습니다. 검색 리스트 번호: {}", searchListNo);
                return searchListNo;
            }
            relatedService.processRelatedEntity(previousSearchListNo, keyword);
            previousSearchListNo = searchEntity.getSearchListNo();
        }
        log.info("검색어 저장 서비스 완료, searchListNo: {}", searchListNo);
        return searchListNo;
    }

    private String checkUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            userId = "guest";
            log.info("사용자 ID가 없습니다. 'guest'로 설정합니다.");
        }
        return userId;
    }

    @Transactional
    private SearchEntity saveSearchEntity(String userId, String keyword) {
        SearchEntity searchEntity = buildSearchEntity(userId, keyword);
        searchRepository.save(searchEntity);
        log.info("검색어 엔티티를 저장했습니다: {}", searchEntity);
        return searchEntity;
    }

    private SearchEntity buildSearchEntity(String userId, String keyword) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setUserId(userId);
        searchEntity.setSearchKeyword(keyword);
        searchEntity.setSearchListTime(LocalDateTime.now());
        log.info("검색어 엔티티 생성 완료");
        return searchEntity;
    }

    @Transactional(readOnly = true)
    public List<SearchEntity> getSearchListByUserId(String userId) {
        List<SearchEntity> searchEntities;
        if (userId == null) {
            searchEntities = searchRepository.findAll();
        } else {
            searchEntities = searchRepository.findByUserIdOrderBySearchListTimeDesc(userId);
        }
        log.info("'{}' 사용자의 최근 검색 리스트 조회 결과: {}", userId, searchEntities);
        return searchEntities;
    }

    @Transactional
    public void deleteSearchKeyword(String userId, int searchListNo) {
        SearchEntity deleteData = searchRepository.findByUserIdAndSearchListNo(userId, searchListNo);
        if (deleteData == null) {
            throw new IllegalArgumentException("검색어 삭제 서비스 - 검색어가 없습니다.");
        }
        searchRepository.delete(deleteData);
        log.info("사용자 {}의 검색어 리스트 번호 {} 삭제 완료", userId, searchListNo);
    }

    @Transactional
    public void deleteAllSearchKeyword(String userId) {
        try {
            searchRepository.deleteByUserId(userId);
            log.info("사용자 {}의 모든 검색어 삭제 완료", userId);
        } catch (Exception e) {
            log.error("사용자 {}의 검색어 전체 삭제 실패", userId, e);
            throw new RuntimeException("사용자 검색어 전체 삭제 실패", e);
        }
    }

}
