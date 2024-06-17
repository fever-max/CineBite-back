package com.cine.back.movieList.service;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import java.util.List;
import java.util.Optional;


@Service
public class MovieListService {
    private final MovieDetailRepository movieDetailRepository;

    public MovieListService (MovieDetailRepository movieDetailRepository){
        this.movieDetailRepository = movieDetailRepository;
    }

    public List<MovieDetailEntity> getMovieGernes(String genre){    // 장르 별 데이터 목록 불러오기
        List<MovieDetailEntity> movieGenres = movieDetailRepository.findByGenres(genre);
        return movieGenres;
    }

    public List<MovieDetailEntity> getMovieActors(String actor){    // 배우 별 데이터 목록 불러오기
        List<MovieDetailEntity> movieActors = movieDetailRepository.findByActors(actor);
        return movieActors;
    }

    public Optional<MovieDetailEntity> getMovieDetail(int movieId){     // 영화 상세정보 번호 불러오기
        Optional<MovieDetailEntity> movieDetail = movieDetailRepository.findByMovieId(movieId);
        return movieDetail;
    }
    
}
