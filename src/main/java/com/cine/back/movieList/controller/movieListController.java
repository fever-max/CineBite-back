package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.service.MovieListService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieListController {
    
    private final MovieDetailRepository movieDetailRepository;
    private final MovieListService movieListService;

    public MovieListController(MovieDetailRepository movieDetailRepository,MovieListService movieListService) {
        this.movieDetailRepository = movieDetailRepository;
        this.movieListService = movieListService;
    }

    //흥행 높은순 정렬
    @GetMapping("/movieList")
    public List<MovieDetailEntity> getMoviePopularity() {
        return movieDetailRepository.findAllByOrderByPopularityAsc();
    }

    // //장르별 정렬
    // @PostMapping("/movieGenres")
    // public ResponseEntity<List<MovieDetailEntity>> getMovieGenres(@RequestBody String gernes) {
    //     try {
    //         List<MovieDetailEntity> movieDetailEntities = movieListService.getMovieGernes(gernes);
    //         return ResponseEntity.ok().body(movieDetailEntities);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    
    // 영화명, 배우, 장르로 검색
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDetailEntity>> searchByKeyword(@PathVariable String keyword) {
        
        log.info("검색 컨트롤러 실행");
        List<MovieDetailEntity> searchResults = movieListService.searchByKeyword(keyword);
        if (searchResults.isEmpty()) {
            log.error("검색한 정보가 없음: {}", keyword);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(searchResults);
        }
        log.info("검색한 정보 반환: {}", searchResults);
        return ResponseEntity.ok(searchResults);
    }

}
    
