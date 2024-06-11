package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.service.MovieListFetcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trendMovie")
public class movieListController {

    private final MovieListFetcher listCall;
    private final TrendMovieRepository trendMovieRepository;

    public movieListController(MovieListFetcher listCall, TrendMovieRepository trendMovieRepository) {
        this.listCall = listCall;
        this.trendMovieRepository = trendMovieRepository;
    }

    @GetMapping("/trend")
    public ResponseEntity<List<TrendMovieEntity>> getAllTrendMovies() {
        try {
            List<TrendMovieEntity> allMovies = listCall.getAllTrendMovies();
            log.info("영화 목록 반환 컨트롤러, trendList : {}", allMovies);

            return ResponseEntity.ok().body(allMovies);
        } catch (Exception e) {
            log.error("리스트 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity <List<TrendMovieEntity>> getAllMovies() {
        try {
                List<TrendMovieEntity> allMovies = trendMovieRepository.findAll();
                return ResponseEntity.ok().body(allMovies);
            } catch (Exception e) {
                log.error("DB에서 영화 목록을 가져오는 데 실패했습니다.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }
    
}
    
