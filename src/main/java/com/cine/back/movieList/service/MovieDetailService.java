package com.cine.back.movieList.service;


import org.springframework.stereotype.Service;

import com.cine.back.movieList.dto.Movie;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.response.TrendMovieResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class MovieDetailService {

    private final MovieDetailRepository movieDetailRepository;
    private final ApiCall apiCall;
    
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
                TrendMovieResponse trendMovieResponse = apiCall.fetchList(page);
                if (trendMovieResponse != null) {
                    List<Movie> movies = trendMovieResponse.getResults();
                    allMovies.addAll(movies);
                }
            } catch (Exception e) {
                System.err.println("페이지 " + page + "에서 오류 발생: " + e.getMessage());
                log.error(page +  "페이지에서 오류 발생 ", e);
            }
        }
        return allMovies;
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

                MovieDetailEntity existingMovie = optionalExistingMovie.get();  // 이미 존재하는 영화 중에 새롭게 갱신되는 영화가 있다면 (존재 목록)
                existingMovie.setTitle(movieDetail.getTitle());
                existingMovie.setOverview(movieDetail.getOverview());
                movieDetailRepository.save(existingMovie);
                log.info("수정된 영화 상세 정보 : {}", existingMovie);

            } else {
                movieDetailRepository.save(movieDetail);
                log.debug("영화 상세 정보 추가 : {}", movieDetail);
            }
        } catch (Exception e) {
            log.error("에러 - 저장된 movieId ", movieDetail.getMovieId(), e);
        }
    }
}