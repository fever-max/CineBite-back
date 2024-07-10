package com.cine.back.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.favorite.entity.UserFavorite;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    
    Optional<List<UserFavorite>> findByUserId(String userId);

    List<UserFavorite> findByMovieId(int movieId);

    void deleteByUserIdAndMovieId(String userId, int movieId);

    boolean existsByUserIdAndMovieId(String userId, int movieId);

    Optional<UserFavorite> findByUserIdAndMovieId(String userId, int movieId);
}
