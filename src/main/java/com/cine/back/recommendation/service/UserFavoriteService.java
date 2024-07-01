package com.cine.back.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.back.recommendation.entity.UserFavorite;
import com.cine.back.recommendation.repository.UserFavoriteRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserFavoriteService {
    
    @Autowired
    private final UserFavoriteRepository userFavoriteRepository;

    public UserFavoriteService(UserFavoriteRepository userFavoriteRepository) {
        this.userFavoriteRepository = userFavoriteRepository;
    }

    public void addFavorite(String userId, int movieId) {
        try {
            if (!userFavoriteRepository.existsByUserIdAndMovieId(userId, movieId)) { // 찜한 상태가 아니라면
                UserFavorite userFavorite = new UserFavorite();
                userFavorite.setUserId(userId);
                userFavorite.setMovieId(movieId);
                userFavoriteRepository.save(userFavorite);
                log.info("찜목록에 성공적으로 추가한 영화 : {}", userFavorite);
            }
        } catch (Exception e) {
            log.error("찜목록에 영화 추가 실패 : {}", e);
            throw e;
        }
    }

    public void deleteFavorite(String userId, int movieId) {
        userFavoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
        log.info("찜 삭제 성공 : {}", userId, movieId);
    }

    public Optional<List<UserFavorite>> favoriteChoice(String userId) {
        try {
            Optional<List<UserFavorite>> userFavorites = userFavoriteRepository.findByUserId(userId);
            log.info("찜목록 조회 성공", userFavorites.get());
            return userFavorites;
        } catch (Exception e) {
            log.error("찜목록 조회 실패 : ", e);
            return Optional.empty();
        }
    }
}
