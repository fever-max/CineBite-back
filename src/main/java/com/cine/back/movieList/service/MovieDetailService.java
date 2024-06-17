package com.cine.back.movieList.service;

import org.springframework.stereotype.Service;

import com.cine.back.config.MovieConfig;
import com.cine.back.movieList.dto.Movie;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.response.MovieResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
@Service
public class MovieDetailService {
    private final MovieDetailRepository movieDetailRepository;
    private final MovieConfig apiCall;
    
    public MovieDetailService(MovieDetailRepository movieDetailRepository,MovieConfig apiCall) {
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

    public List<Movie> fetchAllTrendMovies() {
        List<Movie> allMovies = new ArrayList<>();
    
        for (int page = 1; page <= 1; page++) {
            try {
                MovieResponse trendMovieResponse = apiCall.fetchList(page);
                if (trendMovieResponse != null) {
                    List<Movie> movies = trendMovieResponse.getResults();
                    allMovies.addAll(movies);
                }
            } catch (Exception e) {
                log.error("페이지 " + page + "에서 오류 발생: " + e.getMessage());
            }
        }
        return allMovies != null ? allMovies : List.of();
    }
    
    public void getAllTrendMovies() {
        List<Movie> allMovies = fetchAllTrendMovies();
        if(allMovies!=null){
            for (Movie movie : allMovies) {
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
                log.debug("기존 영화 정보 수정 및 저장", existingMovie);
            } else {
                movieDetailRepository.save(movieDetail);
                log.debug("영화 상세정보 추가", movieDetail);
            }
        } catch (Exception e) {
            log.error("에러 - 저장 실패", movieDetail.getMovieId(), e);
        }
    }
}
