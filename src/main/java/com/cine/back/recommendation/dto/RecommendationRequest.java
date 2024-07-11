package com.cine.back.recommendation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecommendationRequest {
    @NotBlank(message = "[NotBlank] 추천 영화 번호 ")private int movieId;
    @NotBlank(message = "[NotBlank] 추천 영화 제목 ")private String title;
    @NotBlank(message = "[NotBlank] 추천 영화 포스터 ")private String posterPath;
    @NotBlank(message = "[NotBlank] 추천 토마토 지수 ")private double tomatoScore;

    public RecommendationRequest(int movieId, String title, String posterPath, double tomatoScore) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.tomatoScore = tomatoScore;
    }
}

