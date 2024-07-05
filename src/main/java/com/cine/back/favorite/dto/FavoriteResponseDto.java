package com.cine.back.favorite.dto;

public record FavoriteResponseDto(
                Long favoriteId,
                String userId,
                int movieId,
                String posterPath,
                String title) 

{
        public static FavoriteResponseDto of(
            Long favoriteId,
            String userId,
            int movieId,
            String posterPath,
            String title) {
                return new FavoriteResponseDto(
                    favoriteId, 
                    userId, 
                    movieId, 
                    posterPath, 
                    title);
                        }
}
