package com.cine.back.movieList.request;

import jakarta.validation.constraints.NotBlank;

public record UserRatingRequest(
        @NotBlank(message = "[NotBlank] 영화 ID ")int movieId,
        @NotBlank(message = "[NotBlank] 사용자 ID ")String userId,
        @NotBlank(message = "[NotBlank] 평가 내용 ")String rating,
        @NotBlank(message = "[NotBlank] 평가 점수 ")int tomato) { }
