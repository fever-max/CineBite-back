package com.cine.back.movieList.service;


import org.springframework.stereotype.Service;

import com.cine.back.movieList.dto.TrendMovie;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.response.TrendMovieResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
@Service
public class ListCall {

    private final MovieDetailRepository movieDetailRepository;
    private final ApiCall apiCall;
    
    public ListCall(MovieDetailRepository movieDetailRepository,ApiCall apiCall) {
        this.movieDetailRepository = movieDetailRepository;
        this.apiCall = apiCall;
    }

    //서버 실행 시 자동 저장
    @PostConstruct
    public void init() {
        try {
            getAllTrendMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TrendMovie> fetchAllTrendMovies() {
        List<TrendMovie> allMovies = new ArrayList<>();
    
        for (int page = 1; page <= 1; page++) {
            try {
                TrendMovieResponse trendMovieResponse = apiCall.fetchList(page);
                if (trendMovieResponse != null) {
                    List<TrendMovie> movies = trendMovieResponse.getResults();
                    allMovies.addAll(movies);
                }
            } catch (Exception e) {
                System.err.println("페이지 " + page + "에서 오류 발생: " + e.getMessage());
            }
        }
        return allMovies;
    }
    
    public void getAllTrendMovies() {
        List<TrendMovie> allMovies = fetchAllTrendMovies();
        if(allMovies!=null){
            for (TrendMovie movie : allMovies) {
                try {
                    MovieDetailEntity movieDetails = apiCall.fetchMovieDetails(movie.getMovieId());
                    if (movieDetails != null) {
                        saveMovieDetail(movieDetails);
                    }
                } catch (Exception e) {
                    System.err.println("영화 ID " + movie.getMovieId() + "에서 오류 발생: " + e.getMessage());
                }
            }
        }
    }

    // 데이터 저장 (중복 방지)
    private void saveMovieDetail(MovieDetailEntity movieDetail) {
        try {
            Optional<MovieDetailEntity> optionalExistingMovie = movieDetailRepository.findByMovieId(movieDetail.getMovieId());
            if (optionalExistingMovie.isPresent()) {
                MovieDetailEntity existingMovie = optionalExistingMovie.get();
                existingMovie.setTitle(movieDetail.getTitle());
                existingMovie.setOverview(movieDetail.getOverview());
                movieDetailRepository.save(existingMovie);
                log.debug("Updated existing movie: {}", existingMovie);
            } else {
                movieDetailRepository.save(movieDetail);
                log.debug("Saved new movie: {}", movieDetail);
            }
        } catch (Exception e) {
            log.error("Error saving movie with ID {}: ", movieDetail.getMovieId(), e);
        }
    }
}