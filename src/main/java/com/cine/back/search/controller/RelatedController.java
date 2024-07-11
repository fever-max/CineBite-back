package com.cine.back.search.controller;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.service.RelatedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
