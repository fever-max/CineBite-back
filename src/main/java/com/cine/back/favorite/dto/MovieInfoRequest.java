package com.cine.back.favorite.dto;

import jakarta.validation.constraints.NotBlank;

public record MovieInfoRequest(
                @NotBlank(message = "[NotBlank] 찜한 사용자 ID ")int movieId,
                @NotBlank(message = "[NotBlank] 찜한 영화 포스터 ")String posterPath,
                @NotBlank(message = "[NotBlank] 찜한 영화 제목 ")String title,
                @NotBlank(message = "[NotBlank] 찜한 영화의 토마토 지수 ")double tomatoScore) { }
