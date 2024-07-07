package com.cine.back.movieList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Movie Evaluate Info", description = "Evaluate API")
public interface TomatoControllerDocs {

    @Operation(summary = "로튼 토마토 점수", description = "토마토 지수를 보여줍니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "토마토 지수 출력 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 토마토 지수 출력 실패"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 토마토 지수 출력 실패")
    })
    ResponseEntity<String> rateMovie(
            @RequestParam(value = "userId") String userId, 
            @PathVariable(value = "movieId") int movieId,
            @RequestParam(value = "rating") String rating
    );
}
