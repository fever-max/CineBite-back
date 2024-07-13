package com.cine.back.movieList.dto;

import com.cine.back.movieList.request.MovieRatingRequest;
import com.cine.back.movieList.request.UserRatingRequest;

public record Evaluation(
    UserRatingRequest userRatingRequest,
    MovieRatingRequest movieRatingRequest
) { }
