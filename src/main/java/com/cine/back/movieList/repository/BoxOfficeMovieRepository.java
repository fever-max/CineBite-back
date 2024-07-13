package com.cine.back.movieList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.BoxOfficeMovieEntity;

public interface BoxOfficeMovieRepository extends JpaRepository<BoxOfficeMovieEntity, Integer> {
    Optional<BoxOfficeMovieEntity> findByMovieCd(String movieId);

    List<BoxOfficeMovieEntity> findAllByOrderByMovieRankAsc();
}
