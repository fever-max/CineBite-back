package com.cine.back.movieList.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<List<MovieDetailEntity>> getMovieGenres(Genre genre) {
        try {
            Optional<List<MovieDetailEntity>> genresList = movieDetailRepository.findByGenres(genre.getName());
            System.out.println("장르별 조회 서비스------------------" + genre);
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

    // 영화명, 배우, 장르로 검색
    public List<MovieDetailEntity> searchByKeyword(String keyword) {
        log.info("영화 검색 서비스 - 키워드: {}", keyword);
        try {
            List<MovieDetailEntity> searchResults = movieDetailRepository
                    .findByTitleContainingOrCredits_Cast_NameOrGenres_Name(keyword, keyword, keyword);
            if (searchResults.isEmpty()) {
                log.warn("검색 결과 없음 - '{}'에 대한 영화를 찾지 못했습니다.", keyword);
                // 검색 결과가 없을 경우 비슷한 장르의 영화 추천
                return recommendSimilarGenre(keyword);
            }
            return searchResults;
        } catch (Exception e) {
            log.error("영화 검색 서비스 - 키워드 검색 실패", e);
            return List.of();
        }
    }

    // 비슷한 장르 영화 추천
    public List<MovieDetailEntity> recommendSimilarGenre(String genre) {
        log.info("영화 추천 서비스 - 같은 장르 영화 추천: {}", genre);
        try {
            return movieDetailRepository.findByGenres_NameOrderByReleaseDateDesc(genre);
        } catch (Exception e) {
            log.error("영화 추천 서비스 - 같은 장르 영화 추천 실패", e);
            return List.of();
        }
    }
}
