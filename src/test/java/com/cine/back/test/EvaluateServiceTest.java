package com.cine.back.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.UserRatingRepository;
import com.cine.back.movieList.service.EvaluateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
public class EvaluateServiceTest {

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private MovieDetailRepository movieDetailRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @BeforeEach
    public void setUp() {
        // Optional: 데이터베이스 초기화 또는 테스트 데이터 삽입 로직
    }

    @DisplayName("\n + 유저 1의 평가 : 좋음")
    @Test
    public void testRateMovieFreshRating() throws Exception {

        // Given
        int movieId = 1;
        String userId = "user1";
        String rating = "fresh";

        MovieDetailEntity movie = new MovieDetailEntity();
        movie.setMovieId(movieId);
        movie.setFreshCount(0);
        movie.setRottenCount(0);
        movie.setTomatoScore(0.0);
        movieDetailRepository.save(movie); // 실제 데이터베이스에 저장

        // When
        evaluateService.rateMovie(movieId, userId, rating);

        // Then
        MovieDetailEntity updatedMovie = movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않음1!."));

        assertEquals(1, updatedMovie.getFreshCount(), "신선도 테스 에러");
        // assertThat(updatedMovie.getFreshCount()).as("신선도 테스트 에러").isEqualTo(1);
        // assertThat은 List 값이나 문자열 포함 여부 및 길이도 테스트 가능, 복잡한 조건 검사 시 유용
        log.info("신선도는 : {}", updatedMovie.getFreshCount());

        assertEquals(0, updatedMovie.getRottenCount(), "썩음 테스트 에러");
        log.info("썩음은 : {}", updatedMovie.getRottenCount());

        assertEquals(100.0, updatedMovie.getTomatoScore(), "로튼 토마토 테스트 에러");
        log.info("로튼 토마토는  : {}%", updatedMovie.getTomatoScore());
    }

    @DisplayName("\n유저 2의 평가 : 나쁨")
    @Test
    public void testRateMovieRottenRating() throws Exception {

        // Given
        int movieId = 1;
        String userId = "user2";
        String rating = "rotten";

        MovieDetailEntity movie = new MovieDetailEntity();
        movie.setMovieId(movieId);
        movie.setFreshCount(1);
        movie.setRottenCount(0);
        movie.setTomatoScore(100.0);
        movieDetailRepository.save(movie); // 실제 데이터베이스에 저장

        // When
        evaluateService.rateMovie(movieId, userId, rating);

        // Then
        MovieDetailEntity updatedMovie = movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화가 존재하지 않음2!."));
        
        assertEquals(1, updatedMovie.getFreshCount(), "신선도 테스 에러");
        log.info("신선도는 : {}", updatedMovie.getFreshCount());

        assertEquals(1, updatedMovie.getRottenCount(), "썩음 테스트 에러");
        log.info("썩음은 : {}", updatedMovie.getRottenCount());

        assertEquals(50.0, updatedMovie.getTomatoScore(), "로튼 토마토 테스트 에러");
        log.info("로튼 토마토는  : {}%", updatedMovie.getTomatoScore());
    }

    @DisplayName("유저 1 이 이미 평가한 영화입니다.")
    @Test
    public void testRateMovieExistingRating() {

        // Given
        int movieId = 1;
        String userId = "user1";
        String rating = "fresh";

        UserRating existingRating = new UserRating();
        existingRating.setUserId(userId);
        existingRating.setMovieId(movieId);
        userRatingRepository.save(existingRating); // 실제 데이터베이스에 저장

        // When / Then
        assertThrows(Exception.class, () -> evaluateService.rateMovie(movieId, userId, rating));
    }

    @DisplayName("찾을 수 없는 영화입니당")
    @Test
    public void testRateMovieMovieNotFound() {

        // Given
        int movieId = 999; // 존재하지 않는 영화 ID
        String userId = "user3";
        String rating = "fresh";

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> evaluateService.rateMovie(movieId, userId, rating));
    }
}
