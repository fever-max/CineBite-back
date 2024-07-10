package com.cine.back.favorite.dto;

public record FavoriteResponseDto(
                Long favoriteId,
                String userId,
                int movieId,
                String posterPath,
                String title,
                double tomatoScore) 

{
        public static FavoriteResponseDto of(
            Long favoriteId,
            String userId,
            int movieId,
            String posterPath,
            String title,
            double tomatoScore) {
                return new FavoriteResponseDto(
                    favoriteId, 
                    userId, 
                    movieId, 
                    posterPath, 
                    title,
                    tomatoScore);
                        }
}
