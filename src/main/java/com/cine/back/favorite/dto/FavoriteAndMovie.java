package com.cine.back.favorite.dto;

import jakarta.validation.constraints.NotBlank;

public record FavoriteAndMovie(
        @NotBlank(message = "[NotBlank] 영화를 선택한 사용자 정보 ")FavoriteRequestDto favoriteRequestDto,
        @NotBlank(message = "[NotBlank] 찜한 영화 상세정보 ")MovieInfoRequest movieInfoRequest) {
}