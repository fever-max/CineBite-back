package com.cine.back.movieList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.MovieDetailEntity;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Long> {

    Optional<MovieDetailEntity> findByMovieId(int movieId);

    List<MovieDetailEntity> findAllByOrderByPopularityAsc();

    // @Query("SELECT md FROM movie_details md WHERE md.movieId IN (SELECT
    // mg.movieId FROM movie_genres mg WHERE mg.name = :genres)")
    // List<MovieDetailEntity> findByGenres(@Param("genres") String genres);

    // 검색
    List<MovieDetailEntity> findByTitleContainingOrCredits_Cast_NameOrGenres_Name(String title, String castName,
            String genreName);

}
