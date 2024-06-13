package com.cine.back.movieList.controller;

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

    @GetMapping("/list")
    public List<TrendMovieEntity> getFromDB() {
        return trendMovieRepository.findAll();
    }

    //흥행 높은순 정렬
    @GetMapping("/movieList")
    public List<TrendMovieEntity> getMovie() {
        return trendMovieRepository.findAllByOrderByPopularityAsc();
    }
}
    

