package com.cine.back.recommendation.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private int movieId;
    private String title;
    private String posterPath;
    private double tomatoScore;

    public RecommendationRequest(int movieId, String title, String posterPath, double tomatoScore) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.tomatoScore = tomatoScore;
    }
}

