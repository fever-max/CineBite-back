package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cine.back.movieList.entity.TrendMovieEntity;

public interface TrendMovieRepository extends JpaRepository<TrendMovieEntity, Integer>{
    
}
