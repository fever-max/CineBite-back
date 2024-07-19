package com.cine.back.movieList.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cine.back.movieList.dto.Evaluation;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.entity.UserRevalue;
import com.cine.back.movieList.exception.AlreadyEvaluatedException;
import com.cine.back.movieList.exception.EvaluationNotFoundException;
import com.cine.back.movieList.exception.EvaluationNotPermittedException;
import com.cine.back.movieList.exception.MovieNotFoundException;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.UserRatingRepository;
import com.cine.back.movieList.repository.UserRevalueRepository;
import com.cine.back.movieList.request.MovieRatingRequest;
import com.cine.back.movieList.request.UserRatingRequest;
import com.cine.back.movieList.response.EvaluateResponse;
import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateService {
    private final MovieDetailRepository movieDetailRepository;
    private final UserRatingRepository userRatingRepository;
    private final UserRevalueRepository userRevalueRepository;
    private final MovieMapper movieMapper;
    private final UserFavoriteRepository userFavoriteRepository;

    @Transactional
    public EvaluateResponse rateMovie(int movieId, Evaluation evaluation) {
        log.info("# 평가 정보 :{} ", movieId, evaluation);
        UserRatingRequest userRatingRequest = evaluation.userRatingRequest();
        MovieRatingRequest movieRatingRequest = evaluation.movieRatingRequest();

        validateAlreadyEvaluated(userRatingRequest.userId(), userRatingRequest.movieId());
        MovieDetailEntity movie = findMovieById(userRatingRequest.movieId());
        UserRating userRating = movieMapper.toUserRating(userRatingRequest);
        userRatingRepository.save(userRating);

        updateMovieRating(movie, userRatingRequest.rating(), movieRatingRequest);
        movieDetailRepository.save(movie);
        log.info("# 유저 정보 : {} 영화 정보 : {} ", movieId, userRating, movie);
        return movieMapper.toResponse(userRating, movie, null);
    }

    @Transactional
    public void deleteRating(String userId, int movieId) {
        UserRating existingRating = findExistingRating(userId, movieId);
        MovieDetailEntity movie = findMovieById(movieId);

        decrementRatingCount(movie, existingRating);
        updateTomatoScore(movie);
        movieDetailRepository.save(movie);
        userRatingRepository.delete(existingRating);

        saveUserRevalue(movieId, userId);
        log.info("# 삭제한 평가 정보 :{} ", movieId, existingRating);
    }

    // 해당 영화에 대한 평가 유무 확인
    private void validateAlreadyEvaluated(String userId, int movieId) {
        if (userRatingRepository.findByUserIdAndMovieId(userId, movieId).isPresent()) {
            throw new AlreadyEvaluatedException();
        }

        checkRevalueRecord(userId, movieId);
    }

    private void checkRevalueRecord(String userId, int movieId) {
        Optional<UserRevalue> revalueRecord = userRevalueRepository.findByUserIdAndMovieId(userId, movieId);
        if (revalueRecord.isPresent()) {
            UserRevalue userRevalue = revalueRecord.get();
            long secondsRemaining = calculateSecondsRemaining(userRevalue.getDeletedDate());
            
            if (userRevalue.isCheckDeleted() && secondsRemaining > 0) {
                throw new EvaluationNotPermittedException(secondsRemaining);
            }
            if (secondsRemaining <= 0) {
                userRevalueRepository.delete(userRevalue);
            }
        }
    }
    
    // 재평가 시간 한도 설정
    private long calculateSecondsRemaining(LocalDateTime deletedDate) {
        return 60 - ChronoUnit.SECONDS.between(deletedDate, LocalDateTime.now());
    }

    private MovieDetailEntity findMovieById(int movieId) {
        return movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new);
    }

    private UserRating findExistingRating(String userId, int movieId) {
        return userRatingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(EvaluationNotFoundException::new);
    }

    // 삭제 후 토마토 지수 업데이트
    private void decrementRatingCount(MovieDetailEntity movie, UserRating existingRating) {
        if ("fresh".equals(existingRating.getRating())) {
            movie.setFreshCount(movie.getFreshCount() - 1);
        } else if ("rotten".equals(existingRating.getRating())) {
            movie.setRottenCount(movie.getRottenCount() - 1);
        }
    }
    
    // 삭제시점 저장
    private void saveUserRevalue(int movieId, String userId) {
        UserRevalue userRevalue = new UserRevalue(
            movieId,
            userId,
            LocalDateTime.now(),
            true
        );
        userRevalueRepository.save(userRevalue);
    }

    // 토마토 지수 업데이트
    private void updateMovieRating(MovieDetailEntity movie, String rating, MovieRatingRequest movieRatingRequest) {
        incrementRatingCount(movie, rating);
        updateTomatoScore(movie);
    }

    // 영화 평가 지수 계산
    private void incrementRatingCount(MovieDetailEntity movie, String rating) {
        if ("fresh".equals(rating)) {
            movie.setFreshCount(movie.getFreshCount() + 1);
        } else if ("rotten".equals(rating)) {
            movie.setRottenCount(movie.getRottenCount() + 1);
        }
    }

    // 최종 토마토 점수 계산
    private void updateTomatoScore(MovieDetailEntity movie) {
        int totalRatings = movie.getFreshCount() + movie.getRottenCount();
        double tomatoScore = calculateTomatoScore(totalRatings, movie.getFreshCount());
        movie.setTomatoScore(tomatoScore);
        
        updateUserFavoritesTomatoScore(movie);
    }

    // 최종 토마토 점수 계산
    private double calculateTomatoScore(int totalRatings, int freshCount) {
        return totalRatings > 0 ? (double) freshCount / totalRatings * 100 : 0.0;
    }

    // 사용자 평가 변동 시 찜목록 tomatoScore 에 반영 
    private void updateUserFavoritesTomatoScore(MovieDetailEntity movie) {
        List<UserFavorite> userFavorites = userFavoriteRepository.findByMovieId(movie.getMovieId());
        for (UserFavorite userFavorite : userFavorites) {
            userFavorite.setTomatoScore(movie.getTomatoScore());
            userFavoriteRepository.save(userFavorite);
        }
    }
}
