package com.cine.back.movieList.controller;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.service.DetailCall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieDetailController {
    

    private final DetailCall detailCall;
    private final MovieDetailRepository movieDetailRepository;

    public MovieDetailController(DetailCall detailCall, MovieDetailRepository movieDetailRepository) {
        this.detailCall = detailCall;
        this.movieDetailRepository = movieDetailRepository;
    }

    @GetMapping("/detail/{movie_id}")
    public ResponseEntity <MovieDetailEntity> getMovieDetail(@PathVariable("movie_id") int movieId) {
        try {
            MovieDetailEntity movieDetail = detailCall.getMovieDetail(movieId);

            log.info("영화 상세 정보 반환 컨트롤러, detail : {}", movieId);

            return ResponseEntity.ok().body(movieDetail);
        } catch (Exception e) {
            log.info("영화 상세 정보 반환 컨트롤러, detail : {}", movieId);
            log.error("상세 정보 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/list")
    public List<MovieDetailEntity> getFromDBdetail() {
        return movieDetailRepository.findAll();
    }
}