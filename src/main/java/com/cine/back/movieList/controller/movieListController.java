package com.cine.back.movieList.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.service.MovieListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieListController implements MovieListControllerDocs {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    // 흥행 높은순 정렬
    @GetMapping("/movieList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMoviePopularity() {
        log.info("전체 영화 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> allMovieList = movieListService.getAllMovieList();
        return ResponseEntity.ok().body(allMovieList);
    }

    // 장르별 정렬
    @PostMapping("/genresList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieGenres(@RequestBody String genre) {
        log.info("장르별 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> genresList = movieListService.getMovieGernes(genre);
        return ResponseEntity.ok().body(genresList);
    }

    // 배우별 정렬
    @PostMapping("/actorList")
    public ResponseEntity<Optional<List<MovieDetailEntity>>> getMovieActors(@RequestBody String actor) {
        log.info("배우별 조회 컨트롤러");
        Optional<List<MovieDetailEntity>> actorsList = movieListService.getMovieActors(actor);
        return ResponseEntity.ok().body(actorsList);
    }

    // 한 개 영화정보 꺼내기
    @GetMapping("/{movieId}")
    public ResponseEntity<Optional<MovieDetailEntity>> getMovieDetail(@PathVariable int movieId) {
        log.info("영화 상세 조회 컨트롤러");
        Optional<MovieDetailEntity> movieDetail = movieListService.getMovieDetail(movieId);
        return ResponseEntity.ok().body(movieDetail);
    }

}
