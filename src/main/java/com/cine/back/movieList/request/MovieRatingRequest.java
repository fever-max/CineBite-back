package com.cine.back.movieList.request;

public record MovieRatingRequest(
    int freshCount,
    int rottenCount,
    double tomatoScore) { }
