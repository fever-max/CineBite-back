package com.cine.back.movieList.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cine.back.movieList.entity.TrendMovieEntity;

public interface TrendMovieRepository extends JpaRepository<TrendMovieEntity, Integer>{
    
    @Query("SELECT m FROM TrendMovie m WHERE m.movie_id =:movieId")
    Optional<TrendMovieEntity> findByMovieId(String movieId);
}
