package com.cine.back.movieList.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.dto.Genre;
import com.cine.back.movieList.dto.WeeklyBoxOffices;
import com.cine.back.movieList.entity.BoxOfficeMovieEntity;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.BoxOfficeMovieRepository;
import com.cine.back.movieList.repository.MovieDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieListService {
    private final MovieDetailRepository movieDetailRepository;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    public MovieListService(MovieDetailRepository movieDetailRepository,
            BoxOfficeMovieRepository boxOfficeMovieRepository) {
        this.movieDetailRepository = movieDetailRepository;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
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

    public List<WeeklyBoxOffices> getMovieRankingList() {
        List<WeeklyBoxOffices> weeklyBoxOfficesList = new ArrayList<>();
        try {
            List<BoxOfficeMovieEntity> movieRankingList = boxOfficeMovieRepository.findAll();
            for (BoxOfficeMovieEntity list : movieRankingList) {
                Optional<MovieDetailEntity> movieDetailEntityOptional = movieDetailRepository
                        .findByTitle(list.getMovieNm());
                if (movieDetailEntityOptional.isPresent()) {
                    MovieDetailEntity movieDetailEntity = movieDetailEntityOptional.get();
                    WeeklyBoxOffices weeklyBoxOffices = new WeeklyBoxOffices();
                    weeklyBoxOffices.setMovieId(movieDetailEntity.getMovieId());
                    weeklyBoxOffices.setMovieNm(movieDetailEntity.getTitle());
                    weeklyBoxOffices.setMovieRank(list.getMovieRank());
                    weeklyBoxOffices.setRankInTen(list.getRankInTen());
                    weeklyBoxOffices.setRankOldAndNew(list.getRankOldAndNew());
                    weeklyBoxOffices.setPoster_path(movieDetailEntity.getPosterPath());
                    weeklyBoxOfficesList.add(weeklyBoxOffices);
                }
            }
            log.info("박스오피스 전체 리스트 조회 성공");
            return weeklyBoxOfficesList;
        } catch (Exception e) {
            log.error("에러 - 박스오피스 전체 리스트 조회 실패", e);
            throw e;
        }
    }

}
