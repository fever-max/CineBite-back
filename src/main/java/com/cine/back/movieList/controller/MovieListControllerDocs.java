package com.cine.back.movieList.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cine.back.movieList.dto.Cast;
import com.cine.back.movieList.dto.Genre;
import com.cine.back.movieList.dto.WeeklyBoxOffices;
import com.cine.back.movieList.entity.MovieDetailEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Movie", description = "MovieList sort and Detail Info")
public interface MovieListControllerDocs {

        // 흥행순 정렬
        @Operation(summary = "흥행 순 정렬", description = "흥행 순으로 영화를 정렬합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "흥행 순으로 영화 정렬 성공"),
                        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 정렬 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 정렬 실패")
        })
        ResponseEntity<Optional<List<MovieDetailEntity>>> getMoviePopularity();

        // 장르별 정렬
        @Operation(summary = "장르별 정렬", description = "장르별로 영화를 정렬합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "장르별 영화 정렬 성공"),
                        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 정렬 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 정렬 실패")
        })
        ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieGenres(
                        @RequestBody List<Genre> genre);

        // 배우별 정렬
        @Operation(summary = "배우별 정렬", description = "배우별로 영화를 정렬합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "배우별 영화 정렬 성공"),
                        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 정렬 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 정렬 실패")
        })
        ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieActors(
                        @RequestBody Cast cast);

        // 한 개 영화정보 가져오기
        @Operation(summary = "영화 상세 정보 가져오기", description = "영화 ID를 기반으로 영화의 상세 정보를 가져옵니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "영화 상세 정보 가져오기 성공"),
                        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 정보 가져오기 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 정보 가져오기 실패")
        })
        ResponseEntity<Optional<MovieDetailEntity>> getMovieDetail(
                        @PathVariable(value = "movieId") int movieId);

        // 영화 검색
        @Operation(summary = "검색한 영화 정보 반환", description = "검색한 영화 정보를 불러옵니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "검색한 영화 정보 반환 성공"),
                        @ApiResponse(responseCode = "400", description = "검색한 영화 정보 반환 실패") })
        public ResponseEntity<List<MovieDetailEntity>> searchByKeyword(@PathVariable String keyword);

        // 비슷한 장르의 영화 추천
        @Operation(summary = "영화 추천", description = "검색결과가 없을 시, 같으 장르의 정보를 불러옵니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "같은 장르의 영화 정보 반환 성공"),
                        @ApiResponse(responseCode = "400", description = "같은 장르의 영화 정보 반환 실패") })
        public ResponseEntity<List<MovieDetailEntity>> recommendSimilarGenreMovies(@RequestParam String genre);

        // 박스오피스
        @Operation(summary = "흥행 순 정렬", description = "흥행 순으로 영화를 정렬합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "흥행 순으로 영화 정렬 성공"),
                        @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인한 정렬 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 정렬 실패")
        })
        ResponseEntity<List<WeeklyBoxOffices>> getMovieRankingList();
}
