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

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final SearchKeywordService searchKeywordService;

    // 검색어 저장
    @Transactional
    public void saveSearchKeywords(List<SearchKeywordDTO> keywordListDTO, String userId, SearchEntity searchEntity) {
        List<SearchKeywordEntity> savedKeywords = new ArrayList<>();
        for(SearchKeywordDTO keywordDTO : keywordListDTO) {
            SearchKeywordEntity savedKeyword = searchKeywordService.saveSearchKeyword(keywordDTO);
            if (savedKeyword != null) {
                savedKeywords.add(savedKeyword);
                log.info("검색어 저장 성공: {}", keywordDTO.getKeyword());
            } else {
                log.error("검색어 저장 실패: {}", keywordDTO.getKeyword());
            }
        }

        // 검색어들이 존재하면 검색 기록과 연결
        if (!savedKeywords.isEmpty()) {
            searchEntity.setSearchKeywords(savedKeywords);
            searchRepository.save(searchEntity);
            log.info("검색 기록과 검색어 연결 성공");
        } else {
            log.warn("저장된 검색어가 없습니다.");
        }
    }

    // 검색 리스트 DB 저장
    @Transactional
    public void saveSearchData(SaveSearchKeywordsRequest request) {
        List<SearchKeywordDTO> keywordListDTO = request.keywordListDTO();
        String userId = request.userId();
        if (userId == null || userId.isEmpty()) {
            log.warn("사용자 ID가 null이거나 비어 있어서 검색 기록 저장 실패");
            return;
        }

        // 검색 기록 저장
        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setUserId(userId);
        searchEntity.setSearchListTime(LocalDateTime.now());
        searchEntity = searchRepository.save(searchEntity);
        log.info("검색 엔티티 저장 성공: {}", searchEntity.getSearchListNo());

        // 검색어 저장
        saveSearchKeywords(keywordListDTO, userId, searchEntity);
    }
}
