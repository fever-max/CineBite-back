package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.service.MovieListFetcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trendMovie")
public class movieListController {

    private final MovieListFetcher movieListFetcher;
    private final TrendMovieRepository trendMovieRepository;

    @GetMapping("/trend")
    public ResponseEntity<List<TrendMovieEntity>> getAllTrendMovies() {
        try {
            List<TrendMovieEntity> allMovies = movieListFetcher.getAllTrendMovies();
            log.info("영화 목록 반환 컨트롤러, trendList : {}", allMovies);

            return ResponseEntity.ok().body(allMovies);
        } catch (Exception e) {
            log.error("리스트 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public List<TrendMovieEntity> getAllMovies() {
        try {
                return trendMovieRepository.findAll();
            } catch (Exception e) {
                log.error("DB에서 영화 목록을 가져오는 데 실패했습니다.", e);
                return Collections.emptyList();
            }
    }
    
}
    
