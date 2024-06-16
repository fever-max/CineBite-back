package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cine.back.movieList.entity.MovieDetailEntity;

import java.util.*;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Integer> {
    Optional<MovieDetailEntity> findByMovieId(int movieId);
    List<MovieDetailEntity> findAllByOrderByPopularityAsc();
    
    @Query("SELECT md FROM movie_details md JOIN md.genres g WHERE g.name = :genre")
    List<MovieDetailEntity> findByGenres(@Param("genre") String genre);

    @Query("SELECT md FROM movie_details md JOIN md.credits.cast c WHERE c.name = :actor")
    List<MovieDetailEntity> findByActors(@Param("actor") String actor);

    // 영화명, 배우, 장르로 검색
    List<MovieDetailEntity> findByTitleContainingOrCredits_Cast_NameOrGenres_Name(String title, String castName,
          String genreName);

}
