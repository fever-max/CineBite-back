package com.cine.back.favorite.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.favorite.dto.FavoriteAndMovie;
import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.dto.MovieInfoRequest;
import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.exception.handleAddFavoriteFailure;
import com.cine.back.favorite.exception.handleCancelFavoriteFailure;
import com.cine.back.favorite.repository.UserFavoriteRepository;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.config.MovieConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserFavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final UserFavoriteMapper userFavoriteMapper;
    private final MovieConfig movieConfig;
    private final MovieDetailRepository movieDetailRepository;

    @Transactional
    public Optional<FavoriteResponseDto> addFavorite(FavoriteRequestDto favoriteDto) {
        try {
            Optional<UserFavorite> existingFavorite = findExistingFavorite(favoriteDto.userId(), favoriteDto.movieId());
            if (existingFavorite.isPresent()) {
                return cancelFavorite(existingFavorite.get(), favoriteDto); // 이미 찜 상태라면 취소
            } else {
                return addFavoriteIfNotExists(favoriteDto); // 새로운 찜
            }
        } catch (IOException e) {
            log.error("에러 - 영화 찜 요청 실패", e);
            return Optional.empty();
        }
    }
    
    // 이미 찜한 영화인지 검사
    private Optional<UserFavorite> findExistingFavorite(String userId, int movieId) throws IOException {
        return userFavoriteRepository.findByUserIdAndMovieId(userId, movieId);
    }
    
    // 찜 취소하기
    private Optional<FavoriteResponseDto> cancelFavorite(UserFavorite favorite, FavoriteRequestDto favoriteDto) {
        try {
            userFavoriteRepository.delete(favorite);
            log.info("찜 취소된 정보}", favorite);
            return Optional.empty();
        } catch (Exception e) {
            log.error("찜 취소 실패: {}", e.getMessage());
            throw new handleCancelFavoriteFailure();
        }
    }
    
    // 찜 상태가 아니라면 찜목록에 추가하기
    private Optional<FavoriteResponseDto> addFavoriteIfNotExists(FavoriteRequestDto favoriteDto) throws IOException {
        MovieDetailEntity movieDetail = fetchMovieDetails(favoriteDto.movieId());
        FavoriteAndMovie favoriteAndMovie = new FavoriteAndMovie(favoriteDto,
                new MovieInfoRequest(movieDetail.getMovieId(), movieDetail.getPosterPath(), movieDetail.getTitle(), movieDetail.getTomatoScore()));
        try {
            UserFavorite savedFavorite = userFavoriteRepository.save(userFavoriteMapper.toUserFavorite(favoriteAndMovie));
            FavoriteResponseDto responseDto = userFavoriteMapper.toResponseDto(savedFavorite);
            log.info("찜된 영화 정보 : {}", responseDto);
            return Optional.of(responseDto);
        } catch (Exception e) {
            log.error("찜 추가 실패: {}", e.getMessage());
            throw new handleAddFavoriteFailure();
        }
    }
    
    // 외부 API 데이터와 DB 추가 데이터 결합
    public MovieDetailEntity fetchMovieDetails(int movieId) throws IOException {

        // 상세정보에서 영화번호, 포스터, 타이틀(외부API)만 가져오기
        MovieDetailEntity movieDetail = movieConfig.fetchMovieDetails(movieId);
        
        // DB에서 추가 데이터 가져오기
        Optional<MovieDetailEntity> optionalMovieDetail = movieDetailRepository.findByMovieId(movieId);
        if (optionalMovieDetail.isPresent()) {
            MovieDetailEntity dbMovieDetail = optionalMovieDetail.get();
            movieDetail.setTomatoScore(dbMovieDetail.getTomatoScore());
            log.info("토마토 점수 레이팅 : {}", movieDetail.getTomatoScore());
        }
        return movieDetail;
    }

    // 찜한 영화 선택 삭제
    @Transactional
    public void deleteFavorite(String userId, int movieId) {
        userFavoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
        log.info("[DELETE][/favorite/delete] - 찜을 취소한 유저 : {}, 찜목록에서 취소된 영화 :{} ", userId, movieId);
    }
    
    // 찜 목록 불러오기
    public List<FavoriteResponseDto> favoriteList(String userId) {
        log.info("[GET][/favorite/list] - 유저 {}의 찜목록 ", userId);
        List<UserFavorite> userFavorites = userFavoriteRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("찜 목록이 없습니다."));
        return userFavoriteMapper.toResponseDtos(userFavorites);
    }
}
