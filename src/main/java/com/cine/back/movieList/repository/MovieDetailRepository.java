package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cine.back.movieList.entity.MovieDetailEntity;

import java.util.*;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Integer> {
    Optional<MovieDetailEntity> findByMovieId(int movieId);
    List<MovieDetailEntity> findAllByOrderByPopularityAsc();
    
    @Query("SELECT md FROM movie_details md JOIN md.genres g WHERE g.name = :genres")
    List<MovieDetailEntity> findByGenres(@Param("genres") String genres);
    

}
