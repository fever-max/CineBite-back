package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.service.MovieListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieListController implements MovieListControllerDocs{
    
    private final MovieListService movieListService;

    // 영화명, 배우, 장르로 검색
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDetailEntity>> searchByKeyword(@PathVariable(value = "keyword") String keyword) {

        log.info("영화 검색 컨트롤러 실행 - 키워드: {}", keyword);
        try {
            List<MovieDetailEntity> searchResults = movieListService.searchByKeyword(keyword);

            if (searchResults.isEmpty()) {
                log.warn("영화 검색 컨트롤러 - 검색 결과 없음: {}", keyword);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(searchResults);
            }

            log.info("영화 검색 컨트롤러 - 검색 결과 반환: {}", searchResults);
            return ResponseEntity.ok(searchResults);

        }catch (Exception e) {
            log.error("영화 검색 컨트롤러 - 검색 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

     // 같은 장르의 영화 추천
     @GetMapping("/recommend")
    public ResponseEntity<List<MovieDetailEntity>> recommendSimilarGenreMovies(@RequestParam String genre) {
        log.info("영화 추천 컨트롤러 컨트롤러 실행 - 장르: {}", genre);
        try {
            List<MovieDetailEntity> recommendedMovies = movieListService.recommendSimilarGenre(genre);

            if (recommendedMovies.isEmpty()) {
                log.warn("영화 추천 컨트롤러 - 추천할 영화 없음: {}", genre);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(recommendedMovies);
            }

            log.info("영화 추천 컨트롤러 - 추천 영화 반환: {}", recommendedMovies);
            return ResponseEntity.ok(recommendedMovies);

        } catch (Exception e) {
            log.error("영화 추천 컨트롤러 - 추천 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
