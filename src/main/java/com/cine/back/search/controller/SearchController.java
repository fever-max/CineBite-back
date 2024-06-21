package com.cine.back.search.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.SearchDTO;
import com.cine.back.search.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController implements SearchControllerDocs {

    private final SearchService searchService;

    @PostMapping("/saveSearchList")
    public ResponseEntity<Void> saveSearchData(@RequestBody SearchDTO searchDTO) {
        log.info("검색기록 저장 컨트롤러 실행");

        try {
            searchService.saveSearchData(searchDTO);
            log.info("검색기록 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("검색기록 저장 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
