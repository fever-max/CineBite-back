package com.cine.back.favorite.dto;

import jakarta.validation.constraints.NotBlank;

public record FavoriteRequestDto(
                @NotBlank(message = "[NotBlank] 찜한 사용자 ID ")String userId,
                @NotBlank(message = "[NotBlank] 찜한 영화 ID ")int movieId
                ) { }
