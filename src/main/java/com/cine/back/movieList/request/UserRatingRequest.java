package com.cine.back.movieList.request;

public record UserRatingRequest(
        int movieId,
        String userId,
        String rating,
        int tomato) { }
