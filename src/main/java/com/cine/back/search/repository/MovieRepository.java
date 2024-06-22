package com.cine.back.search.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.search.entity.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {

     // 키워드를 포함하는 영화명이나 배우 검색
     List<MovieEntity> findByMovieNmContainingOrActorsContaining(String movieNm, String actors);
}
