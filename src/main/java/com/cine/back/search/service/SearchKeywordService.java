package com.cine.back.search.service;

import org.springframework.stereotype.Service;

import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.entity.SearchKeywordEntity;
import com.cine.back.search.repository.SearchKeywordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    // 단일 검색어 저장
    public SearchKeywordEntity saveSearchKeyword(SearchKeywordDTO keywordDTO) {
        try {
            log.info("검색어 저장 컨트롤러 실행");
            SearchKeywordEntity searchKeywordEntity = new SearchKeywordEntity();
            searchKeywordEntity.setKeyword(keywordDTO.getKeyword());
            return searchKeywordRepository.save(searchKeywordEntity);
        } catch (Exception e) {
            log.error("검색어 저장 실패", e);
            return null;
        }
    }
}
