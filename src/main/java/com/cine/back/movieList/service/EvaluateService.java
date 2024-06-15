// package com.cine.back.movieList.service;

// import java.util.Optional;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.cine.back.movieList.entity.MovieDetailEntity;
// import com.cine.back.movieList.entity.UserRating;
// import com.cine.back.movieList.repository.MovieDetailRepository;
// import com.cine.back.movieList.repository.UserRatingRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class EvaluateService {

//     private final MovieDetailRepository movieDetailRepository;
//     private final UserRatingRepository userRatingRepository;

//     // /**
//     //  * @param userId
//     //  * @param movieId
//     //  * @param rating
//     //  * @throws Exception
//     //  */
//     @Transactional
//     public void rateMovie(String userId, int movieId, String rating) throws Exception {
//         // 유저가 이미 평가했는지 확인
//         Optional<UserRating> existingRating = userRatingRepository.findByUserIdAndMovieId(userId, movieId);
//         if (existingRating.isPresent()) {
//             throw new Exception("유저는 이 영화를 이미 평가했습니다.");
//         }

//         // 영화 상세 정보 조회
//         MovieDetailEntity movie = movieDetailRepository.findByMovieId(movieId);

//         // 새로운 유저 평가 생성
//         UserRating userRating = new UserRating();
//         userRating.setUserId(userId);
//         userRating.setMovieId(movieId);
//         userRating.setRating(rating);
//         userRating.setTomato("fresh".equals(rating) ? 1 : -1); // 평가에 따른 토마토 점수 설정
//         userRatingRepository.save(userRating);

//         // 영화 평가 데이터 업데이트
//         if ("fresh".equals(rating)) {
//             movie.getFreshCount() = 0;
//             movie.setFreshCount(movie.getFreshCount() + 1);
//         } else {
//             movie.setRottenCount(movie.getRottenCount() + 1);
//         }

//         // 총 평가 퍼센티지 계산 및 업데이트
//         int totalRatings = movie.getFreshCount() + movie.getRottenCount();
//         double tomatoScore = (double) movie.getFreshCount() / totalRatings * 100;
//         movie.setTomatoScore(tomatoScore);

//         // 업데이트된 영화 정보 저장
//         movieDetailRepository.save(movie);
//     }
// }
