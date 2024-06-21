package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.cine.back.search.dto.MovieDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MovieInfo", description = "Movie 정보 관련 API입니다.")
public interface MovieControllerDocs {

    @Operation(summary = "검색한 영화 정보 반환", description = "검색한 영화 정보를 불러옵니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색한 영화 정보 반환 성공"),
        @ApiResponse(responseCode = "400", description = "검색한 영화 정보 반환 실패") })
    public ResponseEntity<List<MovieDTO>> searchByKeyword(@PathVariable String keyword);
}
