package com.cine.back.search.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.search.dto.SaveSearchKeywordsRequest;
import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.entity.SearchKeywordEntity;
import com.cine.back.search.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final SearchRepository searchRepository;

    @Transactional
    public void saveSearchList(SaveSearchKeywordsRequest request) {
        try {
            // 유저Id로 SearchEntity를 생성하고 현재 시간을 설정함
            String userId = request.userId();
            List<SearchKeywordDTO> keywordDTOs = request.keywordListDTO();

            if (userId == null || userId.isEmpty()) {
                log.warn("사용자 ID가 null이거나 비어 있어서 검색 기록 저장 실패");
                return;
            }

            SearchEntity searchEntity = createSearchEntity(userId);

            List<SearchKeywordEntity> searchKeywords = createSearchKeywordEntities(keywordDTOs, searchEntity);

            searchEntity.setSearchKeywords(searchKeywords);

            searchRepository.save(searchEntity);
            log.info("[서비스] 검색 기록 저장 성공: {}", searchEntity.getSearchListNo());
        } catch (Exception e) {
            log.error("[서비스] 검색 기록 저장 실패", e);
            throw new RuntimeException("[서비스] 검색 기록 저장 중 오류 발생");
        }
    }

    // 주어진 사용자 ID로 SearchEntity를 생성하고 현재 시간을 설정합니다.
    private SearchEntity createSearchEntity(String userId) {
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setUserId(userId);
        searchEntity.setSearchListTime(LocalDateTime.now());
        return searchEntity;
    }

    // 주어진 keywordDTOs 리스트에서 SearchKeywordEntity 리스트를 생성하고 이를 제공된 searchEntity와 연관시킵니다.
    private List<SearchKeywordEntity> createSearchKeywordEntities(List<SearchKeywordDTO> keywordDTOs, SearchEntity searchEntity) {
        List<SearchKeywordEntity> searchKeywords = new ArrayList<>();
        for (SearchKeywordDTO keywordDTO : keywordDTOs) {
            SearchKeywordEntity searchKeywordEntity = new SearchKeywordEntity();
            searchKeywordEntity.setKeyword(keywordDTO.keyword());
            searchKeywordEntity.setSearchEntity(searchEntity); // SearchEntity와 연관 설정
            searchKeywords.add(searchKeywordEntity);
        }
        return searchKeywords;
    }

    // 사용자 ID에 따른 검색어 리스트 조회
    @Transactional(readOnly = true)
    public List<SearchKeywordDTO> getSearchListByUserId(String userId) {
        List<SearchEntity> searchEntities;

        // userId가 null이면 모든 사용자의 검색어를 조회하도록 처리
        if (userId == null) {
            log.info("모든 사용자의 검색어 조회");
            searchEntities = searchRepository.findAll();
        } else {
            log.info("사용자 {}의 검색어 조회", userId);
            searchEntities = searchRepository.findByUserId(userId);
        }

        List<SearchKeywordDTO> searchKeywordDTOs = new ArrayList<>();

        for (SearchEntity searchEntity : searchEntities) {
            for (SearchKeywordEntity keywordEntity : searchEntity.getSearchKeywords()) {
                searchKeywordDTOs.add(new SearchKeywordDTO(keywordEntity.getSearchKeywordNo(), keywordEntity.getKeyword()));
            }
        }

        return searchKeywordDTOs;
    }

}
