package com.cine.back.movieList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cine.back.movieList.entity.TrendMovieEntity;

public interface TrendMovieRepository extends JpaRepository<TrendMovieEntity, Integer>{
    Optional<TrendMovieEntity> findByMovieId(int movieId);
}
