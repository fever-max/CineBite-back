package com.cine.back.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.search.dto.SearchKeywordDTO;
import com.cine.back.search.entity.SearchKeywordEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SearchKeyword", description = "검색어 저장 관련 API입니다.")
public interface SearchKeywordControllerDocs {

    @Operation(summary = "각 검색어 키워드 저장", description = "검색어 키워드를 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "각 검색어 키워드 저장 성공"),
        @ApiResponse(responseCode = "400", description = "각 검색어 키워드 저장 실패")})
    public ResponseEntity<SearchKeywordEntity> saveSearchKeyword(@RequestBody SearchKeywordDTO searchKeywordDTO);

}
