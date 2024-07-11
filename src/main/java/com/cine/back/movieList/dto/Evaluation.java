package com.cine.back.movieList.dto;

import com.cine.back.movieList.request.MovieRatingRequest;
import com.cine.back.movieList.request.UserRatingRequest;

import jakarta.validation.constraints.NotBlank;

public record Evaluation(
    @NotBlank(message = "[NotBlank] 사용자 영화 평가 정보 ")UserRatingRequest userRatingRequest,
    @NotBlank(message = "[NotBlank] 토마토 지수 정보")MovieRatingRequest movieRatingRequest
) { }
