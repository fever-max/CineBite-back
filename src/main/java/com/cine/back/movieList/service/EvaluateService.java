package com.cine.back.movieList.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.movieList.dto.Evaluation;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.exception.AlreadyEvaluatedException;
import com.cine.back.movieList.exception.EvaluationNotPermittedException;
import com.cine.back.movieList.exception.MovieNotFoundException;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.UserRatingRepository;
import com.cine.back.movieList.request.MovieRatingRequest;
import com.cine.back.movieList.request.UserRatingRequest;
import com.cine.back.movieList.response.EvaluateResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateService {
    private final MovieDetailRepository movieDetailRepository;
    private final UserRatingRepository userRatingRepository;
    private final MovieMapper movieMapper;

    @Transactional
    public EvaluateResponse rateMovie(Evaluation evaluation) throws Exception {

        UserRatingRequest userRatingRequest = evaluation.userRatingRequest();
        MovieRatingRequest movieRatingRequest = evaluation.movieRatingRequest();

        alreadyEvaluate(userRatingRequest.userId(), userRatingRequest.movieId()); // 이미 평가했는지 검증
        MovieDetailEntity movie = findMovieById(userRatingRequest.movieId()); // 선택한 영화 상세정보 저장
        UserRating userRating = movieMapper.toUserRating(userRatingRequest); // 평가와 영화정보 저장
        log.info("평가 정보 저장 테스트 : {}", userRating);
        userRatingRepository.save(userRating);

        updateMovieRating(movie, userRatingRequest.rating(), movieRatingRequest);
        movieDetailRepository.save(movie); // 저장 위치 이동
        log.info("로튼 토마토 지수 : {}", movie.getTomatoScore());
        log.info("평가 정보 : {}", userRating);
        EvaluateResponse responseDto = movieMapper.toResponse(userRating, movie);
        return responseDto;
    }

    @Transactional
    public void deleteRating(String userId, int movieId) throws Exception {
        Optional<UserRating> existingRatingOptional = userRatingRepository.findByUserIdAndMovieId(userId, movieId);
        if (existingRatingOptional.isPresent()) {
            UserRating existingRating = existingRatingOptional.get();
            MovieDetailEntity movie = findMovieById(movieId);

            existingRating.setDeletedDate(LocalDateTime.now()); // 삭제 시간 설정
            
            if ("fresh".equals(existingRating.getRating())) {
                movie.setFreshCount(movie.getFreshCount() - 1);
            }
            if ("rotten".equals(existingRating.getRating())) {
                movie.setRottenCount(movie.getRottenCount() - 1);
            }
            existingRating.setDeletedDate(LocalDateTime.now());
            
            updateTomatoScore(movie);
            movieDetailRepository.save(movie);
            userRatingRepository.delete(existingRating);
        } else {
            throw new Exception("평가를 찾을 수 없습니다.");
        }
    }
    
    private void alreadyEvaluate(String userId, int movieId) {
        Optional<UserRating> existingRating = userRatingRepository.findByUserIdAndMovieId(userId, movieId);
        if (existingRating.isPresent()) {
            LocalDateTime deletedDate = existingRating.get().getDeletedDate();
            if (deletedDate != null && ChronoUnit.MINUTES.between(deletedDate, LocalDateTime.now()) < 1) {
                throw new EvaluationNotPermittedException();  // 삭제된 후 1분이 지나야 평가 가능
            }
            throw new AlreadyEvaluatedException();
        }
    }

    private MovieDetailEntity findMovieById(int movieId) {
        return movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new); // 핸들러
    }
            
    private void updateMovieRating(MovieDetailEntity movie, String rating, MovieRatingRequest movieRatingRequest) {
        if ("fresh".equals(rating)) {
            movie.setFreshCount(movie.getFreshCount() + 1);
        } else if ("rotten".equals(rating)) {
            movie.setRottenCount(movie.getRottenCount() + 1);
        }
        updateTomatoScore(movie);
    }
                
    private void updateTomatoScore(MovieDetailEntity movie) {
        int totalRatings = movie.getFreshCount() + movie.getRottenCount();
        double tomatoScore = (double) movie.getFreshCount() / totalRatings * 100;
        movie.setTomatoScore(tomatoScore);
    }
}