package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.MovieDetailEntity;

import java.util.*;



public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Integer> {
    Optional<MovieDetailEntity> findByMovieId(int movieId);
    List<MovieDetailEntity> findAllByOrderByPopularityAsc();
    
    // @Query("SELECT md FROM movie_details md WHERE md.movieId IN (SELECT mg.movieId FROM movie_genres mg WHERE mg.name = :genres)")
    // List<MovieDetailEntity> findByGenres(@Param("genres") String genres);

}
