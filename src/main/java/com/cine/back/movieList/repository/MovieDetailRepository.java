package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.MovieDetailEntity;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity, Long> {

}
