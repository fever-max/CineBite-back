package com.cine.back.search.controller;

import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.service.RelatedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/related")
@RequiredArgsConstructor
public class RelatedController {

    private final RelatedService relatedService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRelatedEntity(@RequestBody SearchEntity searchEntity,
            @RequestBody String secondaryKeyword) {
        try {
            relatedService.saveRelatedEntity(searchEntity, secondaryKeyword);
            log.info("연관 검색어 저장 요청 처리 완료 - Primary 키워드: '{}', Secondary 키워드: '{}'",
                    searchEntity.getSearchKeyword(), secondaryKeyword);
            return ResponseEntity.ok("연관 검색어 저장 성공");
        } catch (Exception e) {
            log.error("연관 검색어 저장 요청 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("연관 검색어 저장 실패");
        }
    }

}
