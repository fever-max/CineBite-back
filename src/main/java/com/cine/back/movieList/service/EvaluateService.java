package com.cine.back.movieList.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.exception.AlreadyEvaluatedException;
import com.cine.back.movieList.exception.MovieNotFoundException;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.UserRatingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateService {

    private final MovieDetailRepository movieDetailRepository;
    private final UserRatingRepository userRatingRepository;

    @Transactional
    public void rateMovie(int movieId, String userId, String rating) throws Exception {
        AlreadyEvaluate(userId, movieId); // 이미 평가했는지 검증
        MovieDetailEntity movie = findMovieById(movieId);
        UserRating userRating = CreateUserRating(movieId, userId, rating, movie);
        userRatingRepository.save(userRating);
        updateMovieRating(movie, rating);
    }

    private void AlreadyEvaluate(String userId, int movieId) {
        Optional<UserRating> existingRating = userRatingRepository.findByUserIdAndMovieId(userId, movieId);
        if (existingRating.isPresent()) {
            throw new AlreadyEvaluatedException(); // 핸들러
        }
    }

    // 영화 상세 정보 조회
    private MovieDetailEntity findMovieById(int movieId) {
        return movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new); // 핸들러
    }

    public UserRating CreateUserRating(int movieId, String userId, String rating, MovieDetailEntity movie) {
        UserRating userRating = new UserRating();
        userRating.setMovieId(movieId);
        userRating.setUserId(userId);
        userRating.setRating(rating); // 신선도 버튼 누르면 문자열로 "fresh 반환"
        userRating.setTomato(EvaluateUpdate(rating)); // 평가에 따른 토마토 점수 설정
        userRating.setMovieDetailEntity(movie);
        log.info("유저 아이디 : {}", userId);
        log.info("영화 번호 : {}", movieId);
        log.info("해당 유저의 반응 : {}", rating);
        return userRating;
    }

    public int EvaluateUpdate(String rating) {
        if ("fresh".equals(rating)) {
            return 1;
        }
        return -1;
    }

    public void updateMovieRating(MovieDetailEntity movie, String rating) {
        // 총 평가 퍼센티지 계산 및 업데이트
        if ("fresh".equals(rating)) {
            movie.setFreshCount(movie.getFreshCount() + 1);
        }
        if ("rotten".equals(rating)) {
            movie.setRottenCount(movie.getRottenCount() + 1);
        }
        updateTomatoScore(movie);
    }

    public void updateTomatoScore(MovieDetailEntity movie) {
        int totalRatings = movie.getFreshCount() + movie.getRottenCount();
        double tomatoScore = (double) movie.getFreshCount() / totalRatings * 100;
        movie.setTomatoScore(tomatoScore);
        movieDetailRepository.save(movie);
    }
}
