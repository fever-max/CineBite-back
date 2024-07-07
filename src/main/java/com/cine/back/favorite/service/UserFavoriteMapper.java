package com.cine.back.favorite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cine.back.favorite.dto.FavoriteAndMovie;
import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.dto.MovieInfoRequest;
import com.cine.back.favorite.entity.UserFavorite;
@Component
public class UserFavoriteMapper {
    
    public UserFavorite toUserFavorite(FavoriteAndMovie favoriteAndMovie) {
        FavoriteRequestDto favoriteDto = favoriteAndMovie.favoriteRequestDto();
        MovieInfoRequest movieInfoRequest = favoriteAndMovie.movieInfoRequest();

        return UserFavorite.builder()
                .userId(favoriteDto.userId())
                .movieId(movieInfoRequest.movieId())
                .posterPath(movieInfoRequest.posterPath())
                .title(movieInfoRequest.title())
                .tomatoScore((movieInfoRequest.tomatoScore()))
                .build();
    }

    public FavoriteResponseDto toResponseDto(UserFavorite favorite) {
        return FavoriteResponseDto.of(
                favorite.getFavoriteId(),
                favorite.getUserId(),
                favorite.getMovieId(),
                favorite.getPosterPath(),
                favorite.getTitle(),
                favorite.getTomatoScore());
    }

    public List<FavoriteResponseDto> toResponseDtos(List<UserFavorite> userFavorites) {
        return userFavorites.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
