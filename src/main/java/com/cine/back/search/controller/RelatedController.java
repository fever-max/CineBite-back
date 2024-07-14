package com.cine.back.search.controller;

import java.util.List;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.service.RelatedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("related")
@RequiredArgsConstructor
public class RelatedController {

    private final RelatedService relatedService;

    @PostMapping("/save")
    public void saveRelated(@RequestBody SearchRequest searchRequest) {
        log.info("연관 검색어 저장 컨트롤러 실행: {}", searchRequest);
        try {
            relatedService.saveRelatedEntity(searchRequest);
            log.info("연관 검색어 저장 컨트롤러 - 성공. searchListNo: {}",
                    searchRequest.searchListNo());
        } catch (Exception e) {
            log.error("연관 검색어 저장 컨트롤러 - 오류. searchListNo: {}", searchRequest.searchListNo(), e);
        }
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<List<RelatedEntity>> findRelatedByKeyword(@PathVariable(value = "keyword") String keyword) {
        log.info("키워드로 연관 검색어 조회 컨트롤러 실행: {}", keyword);
        List<RelatedEntity> relatedEntities = relatedService.findRelatedByKeyword(keyword);
        if (!relatedEntities.isEmpty()) {
            return ResponseEntity.ok(relatedEntities);
        } else {
            // 연관검색어는 비어있을 수 있음. 그래서 오류가 아님
            log.warn("키워드로 연관 검색어 조회 컨트롤러 - 조회 결과 없음");
            return ResponseEntity.noContent().build();
        }
    }

}
