package com.cine.back.movieList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.UserRating;
import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    Optional<UserRating> findByUserIdAndMovieId(String userId, int movieId);
}
