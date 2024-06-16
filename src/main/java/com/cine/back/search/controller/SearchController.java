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

import com.cine.back.search.dto.SaveSearchKeywordsRequest;
import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/saveSearchList")
    public ResponseEntity<Void> saveSearchData(@RequestBody SaveSearchKeywordsRequest request) {
        log.info("검색어 저장 컨트롤러 실행");

        try {
            searchService.saveSearchList(request);
            log.info("검색어 컨트롤러 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("검색어 컨트롤러 저장 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //사용자 ID에따른 검색어 리스트 조회
    @GetMapping("/user/{userId}") 
    public ResponseEntity<List<SearchKeywordDTO>> getSearchListByUserId(@PathVariable String userId) {
        try {
            log.info("유저ID로 검색어 조회 컨트롤러 실행");
            
            // 로그인 안했을 때 
            if (userId == null) {
                log.warn("사용자 ID가 없습니다. 검색어 조회를 할 수 없습니다.");
                //일단 빈 리스트를 반환하지만, 추가적으로 고민 할 예정,,,,
                //로그인 안했을 땐, 세션ID에 따른 검색어 리스트 조회할까 고민중,,,
                return ResponseEntity.ok().body(List.of());
            }

            //검색 리스트 조회
            List<SearchKeywordDTO> searchKeywordDTOs = searchService.getSearchListByUserId(userId);
            log.info("검색어 조회 성공, 사용자 ID: {}",userId);

            return ResponseEntity.ok(searchKeywordDTOs);
        } catch (Exception e) {
            log.error("유저ID로 검색어 조회 실패", e);
            return ResponseEntity.status(500).build();
        }
    }

}
