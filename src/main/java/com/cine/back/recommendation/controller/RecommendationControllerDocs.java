package com.cine.back.recommendation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.cine.back.recommendation.dto.MovieDetailDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Movie Recommended Info", description = "Recommendation API")
public interface RecommendationControllerDocs {

    // 영화 추천하기
    @Operation(summary = "사용자 영화 추천", description = "다른 사용자들의 찜 목록에 기반해 영화를 추천합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "영화 추천 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 영화 추천 실패"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 영화 추천 실패")
    })
    List<MovieDetailDto> getRecommendations(
         @RequestParam(value = "userId") String userId);
}