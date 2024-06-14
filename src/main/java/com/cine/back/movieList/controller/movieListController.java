package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/api/trendMovie")
public class MovieListController {
    
    private final MovieDetailRepository movieDetailRepository;
    //private final MovieListService movieListService;

    // public MovieListController(MovieDetailRepository movieDetailRepository,MovieListService movieListService) {
    //     this.movieDetailRepository = movieDetailRepository;
    //     this.movieListService = movieListService;
    // }

    public MovieListController(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    @GetMapping("/list")
    public List<MovieDetailEntity> getFromDB() {
        return movieDetailRepository.findAll();
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
}
    
