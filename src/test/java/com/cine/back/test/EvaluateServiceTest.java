package com.cine.back.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.entity.UserRating;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.UserRatingRepository;
import com.cine.back.movieList.service.EvaluateService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EvaluateServiceTest {

    @Mock
    private MovieDetailRepository movieDetailRepositoryMock;

    @Mock
    private UserRatingRepository userRatingRepositoryMock;

    @InjectMocks
    private EvaluateService evaluateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("\n + 유저 1의 평가 : 좋음")
    @Test
    public void testRateMovieFreshRating() throws Exception {

        // Given
        int movieId = 1;
        String userId = "user1";
        String rating = "fresh";    // 서비스에서 1점 얻기

        MovieDetailEntity movie = new MovieDetailEntity();
        movie.setMovieId(movieId);
        movie.setFreshCount(0);
        movie.setRottenCount(0);
        movie.setTomatoScore(0.0);
        movieDetailRepositoryMock.save(movie);  // 엔티티에 무비아이디, 신선함, 썩음, 총점 저장

        when(movieDetailRepositoryMock.findByMovieId(movieId)).thenReturn(Optional.of(movie));
        when(userRatingRepositoryMock.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        // When
        evaluateService.rateMovie(userId, movieId, rating);

        // Then
        verify(movieDetailRepositoryMock, times(1)).save(movie);
        assertEquals(1, movie.getFreshCount() + "신선함이 1인가요?");
        assertEquals(0, movie.getRottenCount());
        assertEquals(100.0, movie.getTomatoScore());
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

        when(movieDetailRepositoryMock.findByMovieId(movieId)).thenReturn(Optional.of(movie));
        when(userRatingRepositoryMock.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        // When
        evaluateService.rateMovie(userId, movieId, rating);

        // Then
        verify(movieDetailRepositoryMock, times(1)).save(movie);
        assertEquals(1, movie.getFreshCount());
        assertEquals(1, movie.getRottenCount());
        assertEquals(50.0, movie.getTomatoScore());
    }

    @DisplayName("\n유저 1 이 이미 평가한 영화입니다.")
    @Test
    public void testRateMovieExistingRating() {
        // Given
        int movieId = 1;
        String userId = "user1";
        String rating = "fresh";

        UserRating existingRating = new UserRating();
        existingRating.setUserId(userId);
        existingRating.setMovieId(movieId);

        when(userRatingRepositoryMock.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.of(existingRating));

        // When / Then
        assertThrows(Exception.class, () -> evaluateService.rateMovie(userId, movieId, rating));
    }
    
    @DisplayName("\n찾을 수 없는 영화입니당")
    @Test
    public void testRateMovieMovieNotFound() {
        // Given
        int movieId = 999;
        String userId = "user3";
        String rating = "fresh";

        when(movieDetailRepositoryMock.findByMovieId(movieId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(Exception.class, () -> evaluateService.rateMovie(userId, movieId, rating));
    }
}
