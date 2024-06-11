package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchKeywordEntity;
import com.cine.back.search.service.SearchKeywordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchKeywordController implements SearchKeywordControllerDocs {

    private final SearchKeywordService searchKeywordService;

    @PostMapping("/saveKeyword")
    public ResponseEntity<SearchKeywordEntity> saveSearchKeyword(@RequestBody SearchKeywordDTO searchKeywordDTO) {
        log.info("단일 검색어 저장 컨트롤러 실행");

        try {
            SearchKeywordEntity savedKeyword = searchKeywordService.saveSearchKeyword(searchKeywordDTO);
            log.info("검색어 저장 성공: {}", searchKeywordDTO.getKeyword());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedKeyword);
        } catch (Exception e) {
            log.error("검색어 저장 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
