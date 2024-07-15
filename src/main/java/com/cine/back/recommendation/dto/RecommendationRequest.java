package com.cine.back.recommendation.dto;

import jakarta.validation.constraints.NotBlank;

public record RecommendationRequest(
    @NotBlank(message = "[NotBlank] 추천 영화 번호 ") int movieId,
    @NotBlank(message = "[NotBlank] 추천 영화 제목 ") String title,
    @NotBlank(message = "[NotBlank] 추천 영화 포스터 ") String posterPath,
    @NotBlank(message = "[NotBlank] 추천 토마토 지수 ") double tomatoScore) { }

