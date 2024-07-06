package com.cine.back.movieList.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.dto.Genre;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieListService {
    private final MovieDetailRepository movieDetailRepository;

    public MovieListService(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    public Optional<List<MovieDetailEntity>> getAllMovieList() {
        try {
            Optional<List<MovieDetailEntity>> allMovieList = movieDetailRepository.findAllByOrderByPopularityAsc();
            log.info("영화 전체 리스트 조회 성공");
            return allMovieList;
        } catch (Exception e) {
            log.error("에러 - 영화 전체 리스트 조회 실패", e);
            throw e;
        }
    }

    public Optional<List<MovieDetailEntity>> getMovieGenres(List<Genre> genres) {
        try {
            List<String> genreNames = genres.stream()
                    .map(Genre::getName)
                    .collect(Collectors.toList());
            Optional<List<MovieDetailEntity>> genresList = movieDetailRepository.findByGenres(genreNames);
            System.out.println("장르별 조회 서비스------------------" + genres);
            log.info("장르별 영화 조회 성공");
            return genresList;
        } catch (Exception e) {
            log.error("에러 - 장르별 영화 조회 실패", e);
            throw e;
        }
    }

    public Optional<List<MovieDetailEntity>> getMovieActors(String actor) {
        try {
            Optional<List<MovieDetailEntity>> actorsList = movieDetailRepository.findByActors(actor);
            log.info("배우별 영화 조회 성공");
            return actorsList;
        } catch (Exception e) {
            log.error("에러 - 배우별 영화 조회 실패", e);
            throw e;
        }
    }

    public Optional<MovieDetailEntity> getMovieDetail(int movieId) {
        try {
            Optional<MovieDetailEntity> movieDetail = movieDetailRepository.findByMovieId(movieId);
            log.info("영화 상세 조회 성공", movieDetail);
            return movieDetail;
        } catch (Exception e) {
            log.error("에러 - 영화 상세 조회 실패", e, movieId);
            throw e;
        }
    }

}
