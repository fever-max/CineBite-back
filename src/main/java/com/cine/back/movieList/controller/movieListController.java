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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieListController implements MovieListControllerDocs{
    
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

    //장르별 정렬
    @PostMapping("/movieGenres")
    public ResponseEntity<List<MovieDetailEntity>> getMovieGenres(@RequestBody String genre) {
        try {
            List<MovieDetailEntity> movieDetailEntity = movieListService.getMovieGernes(genre);
            return ResponseEntity.ok().body(movieDetailEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //배우별 정렬
    @PostMapping("/movieActor")
    public ResponseEntity<List<MovieDetailEntity>> getMovieActors(@RequestBody String actor) {
        try {
            List<MovieDetailEntity> movieDetailEntity = movieListService.getMovieActors(actor);
            return ResponseEntity.ok().body(movieDetailEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //한 개 영화정보 꺼내기
    @GetMapping("/movieDetail/{movieId}")
    public ResponseEntity<Optional<MovieDetailEntity>> getMovieDetail(@PathVariable int movieId) {
        try {
            Optional<MovieDetailEntity> movieDetailEntity = movieListService.getMovieDetail(movieId);
            return ResponseEntity.ok().body(movieDetailEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 영화명, 배우, 장르로 검색
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDetailEntity>> searchByKeyword(@PathVariable(value = "keyword") String keyword) {

        log.info("검색 컨트롤러 실행 - 키워드: {}", keyword);
        try {
            List<MovieDetailEntity> searchResults = movieListService.searchByKeyword(keyword);

            if (searchResults.isEmpty()) {
                log.warn("검색 컨트롤러 - 검색 결과 없음: {}", keyword);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(searchResults);
            }

            log.info("검색 컨트롤러 - 검색 결과 반환: {}", searchResults);
            return ResponseEntity.ok(searchResults);

        }catch (Exception e) {
            log.error("검색 컨트롤러 - 검색 중 오류 발생: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
