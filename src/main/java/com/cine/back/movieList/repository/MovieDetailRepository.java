package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cine.back.movieList.entity.MovieDetailEntity;
import java.util.*;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Integer> {
    
    Optional<List<MovieDetailEntity>> findAllByOrderByPopularityAsc();

    @Query("SELECT md FROM movie_details md WHERE md.movieId = :movieId")
    Optional<MovieDetailEntity> findByMovieId(@Param("movieId") int movieId);

    @Query("SELECT md FROM movie_details md JOIN md.genres g WHERE g.name = :genres")
    Optional<List<MovieDetailEntity>> findByGenres(@Param("genres") String genre);
  
   // 영화명, 배우, 장르로 검색
    List<MovieDetailEntity> findByTitleContainingOrCredits_Cast_NameOrGenres_Name(String title,
            String castName, String genreName);

    // 비슷한 장르의 영화 추천
    public List<MovieDetailEntity> findByGenres_NameOrderByReleaseDateDesc(String genre);

}
