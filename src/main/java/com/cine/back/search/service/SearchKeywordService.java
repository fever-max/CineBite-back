package com.cine.back.search.service;

import org.springframework.stereotype.Service;

import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchKeywordEntity;
import com.cine.back.search.repository.SearchKeywordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    // 각 검색어 저장
    public void saveSearchKeyword(SearchKeywordDTO searchKeywordDTO) {

        try {

            log.info("검색어 저장 컨트롤러 실행");
            SearchKeywordEntity searchKeywordEntity = new SearchKeywordEntity();

            searchKeywordEntity.setKeyword(searchKeywordDTO.getKeyword());

            searchKeywordRepository.save(searchKeywordEntity);
            log.info("검색어 저장 성공");

        } catch (Exception e) {
            log.error("검색어 저장 실패");
        }

    }

}
