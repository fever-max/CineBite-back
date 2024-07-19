package com.cine.back.movieList.request;

import jakarta.validation.constraints.NotBlank;

public record MovieRatingRequest(
    @NotBlank(message = "[NotBlank] 신선도 지수 ")int freshCount,
    @NotBlank(message = "[NotBlank] 썩음 지수 ")int rottenCount,
    @NotBlank(message = "[NotBlank] 토마토 지수 ")double tomatoScore) { }
