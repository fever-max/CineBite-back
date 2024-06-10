package com.cine.back.search.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.search.dto.SearchDTO;
import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.entity.SearchKeywordEntity;
import com.cine.back.search.repository.SearchKeywordRepository;
import com.cine.back.search.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    
    private final SearchRepository searchRepository;

    // 검색 리스트 DB 저장
    public void saveSearchData(SearchDTO searchDTO) {

        if (searchDTO.getSearchKeywords() == null) {
            log.warn("검색어 리스트가 null입니다.");
            return;
        }

        SearchEntity searchEntity = new SearchEntity();

        searchEntity.setSearchListTime(LocalDateTime.now());
        searchEntity.setUserEmail(searchDTO.getUserEmail());

         List<SearchKeywordEntity> searchKeywords = new ArrayList<>();
        for (SearchKeywordDTO keywordDTO : searchDTO.getSearchKeywords()) {
            SearchKeywordEntity searchKeywordEntity = new SearchKeywordEntity();
            searchKeywordEntity.setKeyword(keywordDTO.getKeyword());
            searchKeywordEntity.setSearchEntity(searchEntity);
            searchKeywords.add(searchKeywordEntity);
            
            log.info("검색어 저장 성공: " + keywordDTO.getKeyword());
        }

        searchEntity.setSearchKeywords(searchKeywords);

        // 검색 엔티티를 저장할 때 키워드도 같이 저장
        searchRepository.save(searchEntity);
    }

}
