package com.cine.back.movieList.response;

import java.time.LocalDateTime;

public record EvaluateResponse( 
    Long ratingId,
    int movieId,
    String userId,
    String rating,
    int tomato,
    LocalDateTime deletedDate,
    boolean checkDeleted,
    int freshCount,
    int rottenCount,
    double tomatoScore
    )
{    
    public static EvaluateResponse of(
        Long ratingId,
        int movieId,
        String userId,
        String rating,
        int tomato,
        LocalDateTime deletedDate,
        boolean checkDeleted,
        int freshCount,
        int rottenCount,
        double tomatoScore) {
                            return new EvaluateResponse(
                                ratingId,
                                movieId,
                                userId,
                                rating,
                                tomato,
                                deletedDate,
                                checkDeleted,
                                freshCount,
                                rottenCount,
                                tomatoScore);
                            }
}
