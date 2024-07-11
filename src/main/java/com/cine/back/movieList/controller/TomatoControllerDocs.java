package com.cine.back.movieList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cine.back.movieList.dto.Evaluation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Movie Evaluate Info", description = "Evaluate API")
public interface TomatoControllerDocs {

    // 영화 평가하기
    @Operation(summary = "영화에 대한 평가 정보", description = "사용자가 영화를 평가하고 평가에 맞게 토마토 지수를 보여줍니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토마토 지수 출력 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 토마토 지수 출력 실패"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 토마토 지수 출력 실패")
    })
    ResponseEntity<?> rateMovie(
            @RequestBody Evaluation evaluation, @PathVariable(value = "movieId") int movieId);

    // 영화 평가 삭제하기
    @Operation(summary = "영화에 대한 평가 정보 삭제", description = "사용자 평가를 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 평가 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 사용자 평가 삭제 실패"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 사용자 평가 삭제 실패")
    })
    ResponseEntity<?> deleteRating(
            @RequestParam(value = "userId") String userId,
            @PathVariable(value = "movieId") int movieId);
}
