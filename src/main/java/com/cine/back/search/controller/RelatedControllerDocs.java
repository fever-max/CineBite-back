package com.cine.back.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RelatedSearch", description = "RelatedSearch API")
public interface RelatedControllerDocs {
        @Operation(summary = "연관검색어 저장", description = "사용자가 검색했던 데이터를 기반으로 연관검색어를 저장합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "연관검색어 저장 성공"),
                        @ApiResponse(responseCode = "400", description = "연관검색어 저장 실패")
        })
        public ResponseEntity<String> saveRelatedEntity(@RequestBody String primaryKeyword,
                        @RequestBody String secondaryKeyword);
}
