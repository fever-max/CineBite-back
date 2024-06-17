package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.MovieDetailEntity;

import java.util.*;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Integer> {
  
    Optional<MovieDetailEntity> findByMovieId(int movieId);

    // 영화명, 배우, 장르로 검색
    List<MovieDetailEntity> findByTitleContainingOrCredits_Cast_NameOrGenres_Name(String title,
            String castName, String genreName);

    // 비슷한 장르의 영화 추천
    public List<MovieDetailEntity> findByGenres_NameOrderByReleaseDateDesc(String genre);

}
