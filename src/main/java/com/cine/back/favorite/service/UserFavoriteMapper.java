package com.cine.back.favorite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.entity.UserFavorite;

@Component
public class UserFavoriteMapper {
    
    public UserFavorite toUserFavorite(FavoriteRequestDto favoriteDto) {
        return UserFavorite.builder()
                .userId(favoriteDto.userId())
                .movieId(favoriteDto.movieId())
                .build();
    }

    public FavoriteResponseDto toResponseDto(UserFavorite favorite) {
        return FavoriteResponseDto.of(
                favorite.getFavoriteId(),
                favorite.getUserId(),
                favorite.getMovieId());
    }

    public List<FavoriteResponseDto> toResponseDtos(List<UserFavorite> userFavorites) {
        return userFavorites.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
