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
    
    @PostConstruct
    public void init() {
        try {
            getAllTrendMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Optional<List<MovieDetailEntity>> getAllTrendMovies() {  // 
        List<MovieDetailEntity> movieDetailEntities = new ArrayList<>();
        fetchAllTrendMovies().ifPresent(movies -> movies.forEach(movie -> processMovie(movie, movieDetailEntities)));
        return Optional.of(movieDetailEntities);
    }

    public Optional<List<Movie>> fetchAllTrendMovies() {    // 트렌드 영화 가져오기 
        List<Movie> allMovies = new ArrayList<>();
    
        for (int page = 1; page <= 1; page++) {
            fetchTrendMoviesPage(page).ifPresent(allMovies::addAll);
        }
        return Optional.of(allMovies);
    }

    private Optional<List<Movie>> fetchTrendMoviesPage(int page) {  // 처음 시작 때만 영화 목록들 가져오기
        try {
            MovieResponse trendMovieResponse = apiCall.fetchList(page);
            if (trendMovieResponse != null) {
                return Optional.of(trendMovieResponse.getResults());
            }
        } catch (Exception e) {
            log.error("페이지 {}에서 오류 발생: ", page, e);
        }
        return Optional.empty();
    }

    private void processMovie(Movie movie, List<MovieDetailEntity> movieDetailEntities) {
        try {
            fetchMovieDetails(movie.getMovieId()).ifPresent(movieDetailEntities::add);
        } catch (Exception e) {
            log.error("영화 ID {}에서 오류 발생: ", movie.getMovieId(), e);
        }
    }

    private Optional<MovieDetailEntity> fetchMovieDetails(int movieId) {
        Optional<MovieDetailEntity> optionalMovieDetails = apiCall.fetchMovieDetails(movieId);
        if (optionalMovieDetails.isPresent()) {
            return saveMovieDetail(optionalMovieDetails.get());
        }
        return Optional.empty();
    }

    private Optional<MovieDetailEntity> saveMovieDetail(MovieDetailEntity movieDetail) {
        try {
            Optional<MovieDetailEntity> optionalExistingMovie = movieDetailRepository.findByMovieId(movieDetail.getMovieId());
            if (optionalExistingMovie.isPresent()) {
                return updateExistingMovie(optionalExistingMovie.get(), movieDetail);
            }
            log.info("영화 상세 정보 추가: {}", movieDetail);
            return Optional.of(movieDetailRepository.save(movieDetail));
        } catch (Exception e) {
            log.error("에러 - 저장된 movieId {}: ", movieDetail.getMovieId(), e);
            return Optional.empty();
        }
    }

    // 기존 영화를 업데이트 하는 메서드
    private Optional<MovieDetailEntity> updateExistingMovie(MovieDetailEntity existingMovie, MovieDetailEntity movieDetail) {
        existingMovie.setTitle(movieDetail.getTitle());
        existingMovie.setOverview(movieDetail.getOverview());
        movieDetailRepository.save(existingMovie);
        log.info("수정된 영화 상세 정보: {}", existingMovie.getMovieId());
        return Optional.of(existingMovie);
    }

    // private Optional<MovieDetailEntity> saveNewMovie(MovieDetailEntity movieDetail) {
    //     movieDetailRepository.save(movieDetail); @
    //     log.info("영화 상세 정보 추가: {}", movieDetail);
    //     return Optional.of(movieDetail);
    // }
}
