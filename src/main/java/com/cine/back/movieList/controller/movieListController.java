package com.cine.back.movieList.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.service.ListCall;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/trendMovie")
public class MovieListController {
    
    private final ListCall listCall;
    private final TrendMovieRepository trendMovieRepository;

    public MovieListController(ListCall listCall, TrendMovieRepository trendMovieRepository) {
        this.listCall = listCall;
        this.trendMovieRepository = trendMovieRepository;
    }

    // @GetMapping("/trend")
    // public ResponseEntity<List<TrendMovieEntity>> getAllTrendMovies() {
    //     try {
    //         List<TrendMovieEntity> allMovies = listCall.getAllTrendMovies();
    //         log.info("영화 목록 반환 컨트롤러, trendList : {}", allMovies);

    //         return ResponseEntity.ok().body(allMovies);
    //     } catch (Exception e) {
    //         log.error("리스트 반환 실패 : ", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    @GetMapping("/list")
    public List<TrendMovieEntity> getFromDB() {
        return trendMovieRepository.findAll();
    }

    public ResponseEntity<List<TrendMovieEntity>> saveMovies() {// 매핑 없이 내부 메서드로 정의
        try {
            List<TrendMovieEntity> allMovies = listCall.getAllTrendMovies();
            log.info("영화 목록 반환 컨트롤러, trendList : {}", allMovies);
            return ResponseEntity.ok().body(allMovies);
        } catch (Exception e) {
            log.error("리스트 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
    

