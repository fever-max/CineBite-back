package com.cine.back.movieList.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.request.MovieRatingRequest;
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
                // .deletedDate(LocalDateTime.now())
                .build();
    }

    public MovieDetailEntity toMovieDetailEntity(MovieRatingRequest movieRequest){
        return MovieDetailEntity.builder()
                    .freshCount(movieRequest.freshCount())
                    .rottenCount(movieRequest.rottenCount())
                    .tomatoScore(movieRequest.tomatoScore())
                    .build();
    }

    public EvaluateResponse toResponse(UserRating userRating, MovieDetailEntity movieDetail) {
        return EvaluateResponse.of(
                userRating.getRatingId(),
                userRating.getMovieId(),
                userRating.getUserId(),
                userRating.getRating(),
                userRating.getTomato(),
                userRating.getDeletedDate(), // 삭제하지 않았다면 null 값줘야할 수도
                userRating.isCheckDeleted(),
                movieDetail.getFreshCount(),
                movieDetail.getRottenCount(),
                movieDetail.getTomatoScore()
        );
    }

    public MovieDetailEntity updateEvaluate(MovieDetailEntity ratingEntity, MovieRatingRequest ratingDto) {
        ratingEntity.setFreshCount(ratingDto.freshCount());
        ratingEntity.setRottenCount(ratingDto.rottenCount());
        ratingEntity.setTomatoScore(ratingDto.tomatoScore());
        return ratingEntity;
    }
}
