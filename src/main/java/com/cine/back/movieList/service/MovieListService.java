package com.cine.back.movieList.service;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import java.util.*;

@Service
public class MovieListService {
    private final MovieDetailRepository movieDetailRepository;

    public MovieListService (MovieDetailRepository movieDetailRepository){
        this.movieDetailRepository = movieDetailRepository;
    }
    //(진행중)
    public List<MovieDetailEntity> getMovieGernes(String genres){
        System.out.println("서비스: "+genres);
        List<MovieDetailEntity> movieGenres = movieDetailRepository.findByGenres(genres);
        return movieGenres;
    }
    
}
