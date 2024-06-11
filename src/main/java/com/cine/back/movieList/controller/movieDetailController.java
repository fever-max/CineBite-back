package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.movieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.service.MovieDetailFetcher;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Collections;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class movieDetailController {

    private final MovieDetailFetcher detailCall;
    private final MovieDetailRepository movieDetailRepository;

    public movieDetailController(MovieDetailFetcher detailCall, MovieDetailRepository movieDetailRepository) {
        this.detailCall = detailCall;
        this.movieDetailRepository = movieDetailRepository;
    }

    @GetMapping("/detail/{movie_id}")
    public ResponseEntity <movieDetailEntity> getMovieDetail(@PathVariable("movie_id") int movieId) {
        try {
            movieDetailEntity movieDetail = detailCall.getMovieDetail(movieId);
            log.info("영화 상세 정보 반환 컨트롤러, detail : {}", movieId);

            return ResponseEntity.ok().body(movieDetail);
        } catch (Exception e) {
            log.info("영화 상세 정보 반환 컨트롤러, detail : {}", movieId);
            log.error("상세 정보 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/info")
    public List<movieDetailEntity> getFromDBdetail() {
        try {
        return movieDetailRepository.findAll();
    } catch (Exception e) {
        log.error("DB에서 영화 상세 정보 가져오지 못함. ", e);
        // return new ArrayList<>(); 애는 성능 저하
        return Collections.emptyList(); // 빈 리스트 반환, Map일 경우 Collections.emptyMap() 사용
    }
}
}
