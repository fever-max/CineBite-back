package com.cine.back.movieList.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.response.TrendMovieResponse;
import com.cine.back.movieList.service.ListCall;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("localhost:3000")
@RequestMapping("/api/trendMovie")
public class movieListController {

    private final ListCall listCall;
    private final TrendMovieRepository trendMovieRepository;

    public movieListController(ListCall listCall, TrendMovieRepository trendMovieRepository) {
        this.listCall = listCall;
        this.trendMovieRepository = trendMovieRepository;
    }

    @GetMapping("/trend")
    public ResponseEntity<TrendMovieResponse> getTrendInfo() {
        try {
            TrendMovieResponse trendMovieResponse = listCall.getTrendMovieList();
            
            log.info("영화 목록 반환 컨트롤러, trendList : {}", trendMovieResponse);
            return ResponseEntity.ok(trendMovieResponse);
            // return ResponseEntity.ok(listCall.getTrendMovieList());
        } catch (Exception e) {
            log.error("리스트 반환 실패 : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin("localhost:3000")
    @GetMapping("/list")
    public List<TrendMovieEntity> getFromDB() {
        return trendMovieRepository.findAll();
    }
    
}
    
