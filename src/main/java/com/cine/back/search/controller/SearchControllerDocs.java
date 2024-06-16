package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.search.dto.SaveSearchKeywordsRequest;
import com.cine.back.search.dto.SearchKeywordDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SearchList", description = "최근검색어 관련 API입니다.")
public interface SearchControllerDocs {

    @Operation(summary = "사용자의 검색기록 저장", description = "사용자의 검색기록을 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자의 검색기록 저장 성공"),
        @ApiResponse(responseCode = "400", description = "사용자의 검색기록 저장 실패")})
        public ResponseEntity<Void> saveSearchData(@RequestBody SaveSearchKeywordsRequest request);


        
    @Operation(summary = "사용자ID로 검색기록 조회", description = "사용자의 검색기록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자ID를 통해 검색어 조회 성공"),
        @ApiResponse(responseCode = "400", description = "사용자ID를 통해 검색어 조회 실패")})
      public ResponseEntity<List<SearchKeywordDTO>> getSearchListByUserId(@PathVariable String userId);

}
