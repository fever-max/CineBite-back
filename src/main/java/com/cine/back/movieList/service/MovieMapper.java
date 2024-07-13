package com.cine.back.movieList.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.entity.UserRevalue;
import com.cine.back.movieList.request.UserRatingRequest;
import com.cine.back.movieList.response.EvaluateResponse;

@Component
public class MovieMapper {
    
    public UserRating toUserRating(UserRatingRequest userRatingRequest) {
        return UserRating.builder()
                .movieId(userRatingRequest.movieId())
                .userId(userRatingRequest.userId())
                .rating(userRatingRequest.rating())
                .tomato(userRatingRequest.tomato())
                .build();
    }

    public EvaluateResponse toResponse(UserRating userRating, MovieDetailEntity movieDetail, UserRevalue userRevalue) {
        LocalDateTime deletedDate = userRevalue != null ? userRevalue.getDeletedDate() : null;
        boolean checkDeleted = userRevalue != null && userRevalue.isCheckDeleted();

        return EvaluateResponse.of(
                userRating.getRatingId(),
                userRating.getMovieId(),
                userRating.getUserId(),
                userRating.getRating(),
                userRating.getTomato(),
                deletedDate, // 삭제하지 않았다면 null 값
                checkDeleted,
                movieDetail.getFreshCount(),
                movieDetail.getRottenCount(),
                movieDetail.getTomatoScore()
        );
    }
}
