package com.cine.back.favorite.dto;

public record FavoriteResponseDto(
                Long favoriteId,
                String userId,
                int movieId) 

{
        public static FavoriteResponseDto of(
            Long favoriteId,
            String userId,
            int movieId) {
                return new FavoriteResponseDto(favoriteId, userId, movieId);
            }
}
