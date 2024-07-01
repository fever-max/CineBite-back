package com.cine.back.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.recommendation.entity.UserFavorite;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Integer> {
    Optional<List<UserFavorite>> findByUserId(String userId);

    boolean existsByUserIdAndMovieId(String userId, int movieId);

    void deleteByUserIdAndMovieId(String userId, int movieId);
}
