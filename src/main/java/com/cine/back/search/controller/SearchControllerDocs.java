package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.SearchEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Search", description = "Search API")
public interface SearchControllerDocs {

        @Operation(summary = "사용자의 검색어 저장", description = "사용자의 검색어를 저장합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "사용자의 검색어 저장 성공"),
                        @ApiResponse(responseCode = "400", description = "사용자의 검색어 저장 실패") })
        public ResponseEntity<Integer> saveSearchList(@RequestBody SearchRequest request);

        @Operation(summary = "사용자ID로 검색기록 조회", description = "사용자의 검색기록을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "사용자ID를 통해 검색어 조회 성공"),
                        @ApiResponse(responseCode = "400", description = "사용자ID를 통해 검색어 조회 실패") })
        public ResponseEntity<List<SearchEntity>> getSearchListByUserId(@PathVariable(value = "userId") String userId);

        @Operation(summary = "사용자ID로 검색기록 삭제", description = "사용자의 검색기록을 삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "사용자ID를 통해 검색어 삭제 성공"),
                        @ApiResponse(responseCode = "400", description = "사용자ID를 통해 검색어 삭제 실패") })
        public ResponseEntity<String> deleteSearchKeyword(@PathVariable(value = "userId") String userId,
                        @PathVariable(value = "searchListNo") int searchListNo);

        @Operation(summary = "사용자ID로 검색기록 전체삭제", description = "사용자의 검색기록을 전체삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "사용자ID를 통해 검색어 전체삭제 성공"),
                        @ApiResponse(responseCode = "400", description = "사용자ID를 통해 검색어 전체삭제 실패") })
        public ResponseEntity<String> deleteAllSearchKeyword(@PathVariable(value = "userId") String userId);
}