package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
            log.info("검색어 저장 컨트롤러 - 성공");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("검색어 저장 컨트롤러 - 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SearchEntity>> getSearchListByUserId(@PathVariable(value = "userId") String userId) {
        log.info("사용자 검색어 조회 컨트롤러 실행 - userId: {}", userId);
        try {
            List<SearchEntity> searchEntities = searchService.getSearchListByUserId(userId);
            return ResponseEntity.ok(searchEntities);
        } catch (IllegalArgumentException e) {
            log.error("검색어 조회 컨트롤러 - 사용자 ID가 잘못되었습니다.", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("검색어 조회 컨트롤러 - 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{userId}/{searchListNo}")
    public ResponseEntity<String> deleteSearchKeyword(
            @PathVariable(value = "userId") String userId,
            @PathVariable(value = "searchListNo") int searchListNo) {
        log.info("사용자 검색어 삭제 컨트롤러 실행 - userId: {}, searchListNo: {}", userId, searchListNo);
        try {
            searchService.deleteSearchKeyword(userId, searchListNo);
            return ResponseEntity.ok("검색어 삭제 성공");
        } catch (Exception e) {
            log.error("사용자 검색어 삭제 컨트롤러 실패 - 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("검색어 삭제 실패 - 서버 오류");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteAllSearchKeyword(@PathVariable(value = "userId") String userId) {
        log.info("사용자 검색어 전체삭제 컨트롤러 실행 - userId: {}", userId);
        try {
            searchService.deleteAllSearchKeyword(userId);
            return ResponseEntity.ok("검색어 전체삭제 성공");
        } catch (Exception e) {
            log.error("사용자 검색어 전체삭제 컨트롤러 실패 - 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("검색어 전체삭제 실패 - 서버 오류");
        }
    }

}
