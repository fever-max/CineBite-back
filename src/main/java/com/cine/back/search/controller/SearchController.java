package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController implements SearchControllerDocs {

    private final SearchService searchService;

    @PostMapping("/saveSearchList")
    public ResponseEntity<Void> saveSearchData(@RequestBody SearchRequest request) {
        log.info("검색어 저장 컨트롤러 실행");

        try {
            searchService.saveSearchList(request);
            log.info("검색어 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("검색어 저장 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SearchEntity>> getSearchListByUserId(@PathVariable(value = "userId") String userId) {
        log.info("사용자 {}의 검색어 조회", userId);

        try {
            List<SearchEntity> searchEntities = searchService.getSearchListByUserId(userId);
            return ResponseEntity.ok(searchEntities);
        } catch (IllegalArgumentException e) {
            log.error("사용자 ID가 잘못되었습니다.", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("검색어 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
