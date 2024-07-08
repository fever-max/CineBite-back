package com.cine.back.recommendation.dto;

public record RecommendationRequest(
        int movieId,
        String title,
        String posterPath,
        double tomatoScore
) { }
