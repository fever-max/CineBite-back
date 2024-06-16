package com.cine.back.movieList.service;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
@Slf4j
@Service
public class MovieListService {
    private final MovieDetailRepository movieDetailRepository;

    public MovieListService (MovieDetailRepository movieDetailRepository){
        this.movieDetailRepository = movieDetailRepository;
    }

    public List<MovieDetailEntity> getMovieGernes(String genre){
        List<MovieDetailEntity> movieGenres = movieDetailRepository.findByGenres(genre);
        return movieGenres;
    }

    public List<MovieDetailEntity> getMovieActors(String actor){
        List<MovieDetailEntity> movieActors = movieDetailRepository.findByActors(actor);
        return movieActors;
    }

    public Optional<MovieDetailEntity> getMovieDetail(int movieId){
        Optional<MovieDetailEntity> movieDetail = movieDetailRepository.findByMovieId(movieId);
        return movieDetail;
    }

  // 영화명, 배우, 장르로 검색
  public List<MovieDetailEntity> searchByKeyword(String keyword) {

    try {
        log.info("검색할 키워드: {}", keyword);
        return movieDetailRepository.findByTitleContainingOrCredits_Cast_NameOrGenres_Name(keyword, keyword,
                keyword);
    } catch (Exception e) {
        log.error("키워드 검색 실패", e);
        return Collections.emptyList(); // 빈 리스트 반환
    }

}
    
}
