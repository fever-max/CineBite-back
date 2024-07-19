package com.cine.back.movieList.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.UserRevalue;

public interface UserRevalueRepository extends JpaRepository<UserRevalue, Long> {

    Optional<UserRevalue> findByUserIdAndMovieId(String userId, int movieId);
}