package com.cine.back.favorite.dto;

public record FavoriteAndMovie(
        FavoriteRequestDto favoriteRequestDto,
        MovieInfoRequest movieInfoRequest) {
}