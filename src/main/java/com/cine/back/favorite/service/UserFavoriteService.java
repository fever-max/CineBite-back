package com.cine.back.favorite.service;

import org.springframework.stereotype.Service;

import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.exception.handleAddFavoriteFailure;
import com.cine.back.favorite.exception.handleCancelFavoriteFailure;
import com.cine.back.favorite.repository.UserFavoriteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserFavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final UserFavoriteMapper userFavoriteMapper;

    public UserFavoriteService(UserFavoriteRepository userFavoriteRepository, UserFavoriteMapper userFavoriteMapper) {
        this.userFavoriteRepository = userFavoriteRepository;
        this.userFavoriteMapper = userFavoriteMapper;
    }

    @Transactional
    public Optional<FavoriteResponseDto> addFavorite(FavoriteRequestDto favoriteDto) {
    String userId = favoriteDto.userId();
    int movieId = favoriteDto.movieId();

    Optional<UserFavorite> existingFavorite = userFavoriteRepository.findByUserIdAndMovieId(userId, movieId);

    existingFavorite.ifPresent(favorite -> {
        try {
            userFavoriteRepository.delete(favorite);
            log.info("찜 취소: 유저 {}, 영화번호 {}", userId, movieId);
        } catch (Exception e) {
            log.error("찜 취소 실패: {}", e.getMessage());
            throw new handleCancelFavoriteFailure();
        }
    });
        // 원래 없으면 찜 추가
        if (!existingFavorite.isPresent()) {
            return addFavoriteIfNotExists(favoriteDto);
        }
            return Optional.empty();
    }
    private Optional<FavoriteResponseDto> addFavoriteIfNotExists(FavoriteRequestDto favoriteDto) {
    String userId = favoriteDto.userId();
    int movieId = favoriteDto.movieId();

    try {
        UserFavorite userFavorite = userFavoriteMapper.toUserFavorite(favoriteDto);
        UserFavorite savedFavorite = userFavoriteRepository.save(userFavorite);
        FavoriteResponseDto responseDto = userFavoriteMapper.toResponseDto(savedFavorite);
        log.info("찜 추가: 유저 {}, 영화번호 {}", userId, movieId);
        return Optional.of(responseDto);
    } catch (Exception e) {
        log.error("찜 추가 실패: {}", e.getMessage());
        throw new handleAddFavoriteFailure();
        }
    }

    @Transactional
    public void deleteFavorite(String userId, int movieId) {
        userFavoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public List<FavoriteResponseDto> favoriteList(String userId) {
        Optional<List<UserFavorite>> userFavorites = userFavoriteRepository.findByUserId(userId);
        return userFavorites.map(userFavoriteMapper::toResponseDtos)
                            .orElseThrow(() -> new RuntimeException("찜 목록이 없습니다."));
    }
}
