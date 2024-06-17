package com.cine.back.movieList.service;


import org.springframework.stereotype.Service;

import com.cine.back.config.MovieConfig;
import com.cine.back.movieList.dto.Movie;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.response.MovieResponse;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class MovieDetailService {

    private final MovieDetailRepository movieDetailRepository;
    private final MovieConfig apiCall;
    
    //서버 실행 시 자동 저장
    @PostConstruct
    public void init() {
        try {
            getAllTrendMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<List<Movie>> fetchAllTrendMovies() {
        List<Movie> allMovies = new ArrayList<>();
    
        for (int page = 1; page <= 1; page++) {
            try {
                MovieResponse trendMovieResponse = apiCall.fetchList(page);
                if (trendMovieResponse != null) {
                    List<Movie> movies = trendMovieResponse.getResults();
                    allMovies.addAll(movies);
                    Optional.of(allMovies);
                }
            } catch (Exception e) {
                System.err.println("페이지 " + page + "에서 오류 발생: " + e.getMessage());
                log.error(page +  "페이지에서 오류 발생 ", e);
                return Optional.empty();   // 오류 발생 시 빈 리스트 반환 ? / 아니면 오류 발생 전까지 데이터 그대로 반환하는지
            }
        }
        return Optional.of(allMovies);
    }
    
    public Optional<List<MovieDetailEntity>> getAllTrendMovies() {
        Optional<List<Movie>> optionalMovies = fetchAllTrendMovies();
        List<MovieDetailEntity> movieDetailEntities = new ArrayList<>();

        if(optionalMovies.isPresent()){
            List<Movie> allMovies = optionalMovies.get();
            for (Movie movie : allMovies) {
                try {
                    Optional<MovieDetailEntity> optionalMovieDetails = apiCall.fetchMovieDetails(movie.getMovieId());
                    if (optionalMovieDetails.isPresent()) {
                        MovieDetailEntity movieDetailEntity = saveMovieDetail(optionalMovieDetails.get()).orElse(null);
                        if (movieDetailEntity != null) {
                            movieDetailEntities.add(movieDetailEntity);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("영화 ID " + movie.getMovieId() + "에서 오류 발생: " + e.getMessage());
                    log.error("영화 ID " + movie.getMovieId() + "에서 오류 발생: ", e);
                    // 오류가 발생하더라도 남은 영화를 계속 처리합니다.
                }
            }
            return Optional.of(movieDetailEntities);
        } else {
            return Optional.empty();
        }
    }

    // 데이터 저장 (중복 방지)
    private Optional<MovieDetailEntity> saveMovieDetail(MovieDetailEntity movieDetail) {
        try {
            Optional<MovieDetailEntity> optionalExistingMovie = movieDetailRepository.findByMovieId(movieDetail.getMovieId());

            if (optionalExistingMovie.isPresent()) {
                MovieDetailEntity existingMovie = optionalExistingMovie.get();  // 이미 존재하는 영화 중에 새롭게 갱신되는 영화가 있다면 (존재 목록)
                existingMovie.setTitle(movieDetail.getTitle());
                existingMovie.setOverview(movieDetail.getOverview());
                movieDetailRepository.save(existingMovie);
                log.info("수정된 영화 상세 정보 : {}", existingMovie);
                return Optional.of(existingMovie);
            } else {
                movieDetailRepository.save(movieDetail);
                log.info("영화 상세 정보 추가 : {}", movieDetail);
                return Optional.of(movieDetail);    // 응답 값이 포함되어 있다면 해당 값을 포함하는 optional 객체 생성
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("에러 - 저장된 movieId ", movieDetail.getMovieId(), e);
            return Optional.empty();    // 응답 값이 null 일 경우 비어있는 optional 객체 생성
        }
    }
}