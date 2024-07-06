// package com.cine.back.test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.cine.back.movieList.entity.MovieDetailEntity;
// import com.cine.back.movieList.entity.UserRating;
// import com.cine.back.movieList.exception.AlreadyEvaluatedException;
// import com.cine.back.movieList.exception.MovieNotFoundException;
// import com.cine.back.movieList.repository.MovieDetailRepository;
// import com.cine.back.movieList.repository.UserRatingRepository;

// @ExtendWith(MockitoExtension.class)
// public class EvaluateServiceTest {

// @Mock
// private MovieDetailRepository movieDetailRepository;

// @Mock
// private UserRatingRepository userRatingRepository;

// @InjectMocks
// private EvaluateService evaluateService;

// private MovieDetailEntity movieDetailEntity;
// private UserRating userRating;

// @BeforeEach
// void setUp() {
// movieDetailEntity = new MovieDetailEntity();
// movieDetailEntity.setMovieId(1);
// movieDetailEntity.setTitle("Test Movie");
// movieDetailEntity.setFreshCount(0);
// movieDetailEntity.setRottenCount(0);

// userRating = new UserRating();
// userRating.setUserId("user123");
// userRating.setMovieId(1);
// userRating.setRating("fresh");
// }

// @Test
// void testRateMovie_Success() throws Exception {
// when(userRatingRepository.findByUserIdAndMovieId(anyString(),
// anyInt())).thenReturn(Optional.empty());
// when(movieDetailRepository.findByMovieId(anyInt())).thenReturn(Optional.of(movieDetailEntity));

// evaluateService.rateMovie(1, "user123", "fresh");

// verify(userRatingRepository, times(1)).save(any(UserRating.class));
// verify(movieDetailRepository, times(1)).save(any(MovieDetailEntity.class));
// }

// @Test
// void testRateMovie_AlreadyEvaluated() {
// when(userRatingRepository.findByUserIdAndMovieId(anyString(),
// anyInt())).thenReturn(Optional.of(userRating));

// assertThrows(AlreadyEvaluatedException.class, () -> {
// evaluateService.rateMovie(1, "user123", "fresh");
// });

// verify(userRatingRepository, times(0)).save(any(UserRating.class));
// verify(movieDetailRepository, times(0)).save(any(MovieDetailEntity.class));
// }

// @Test
// void testRateMovie_MovieNotFound() {
// when(userRatingRepository.findByUserIdAndMovieId(anyString(),
// anyInt())).thenReturn(Optional.empty());
// when(movieDetailRepository.findByMovieId(anyInt())).thenReturn(Optional.empty());

// assertThrows(MovieNotFoundException.class, () -> {
// evaluateService.rateMovie(1, "user123", "fresh");
// });

// verify(userRatingRepository, times(0)).save(any(UserRating.class));
// verify(movieDetailRepository, times(0)).save(any(MovieDetailEntity.class));
// }

// @Test
// void testCreateUserRating() {
// UserRating newRating = evaluateService.CreateUserRating(1, "user123",
// "fresh", movieDetailEntity);
// assertNotNull(newRating);
// assertEquals("user123", newRating.getUserId());
// assertEquals(1, newRating.getMovieId());
// assertEquals("fresh", newRating.getRating());
// assertEquals(1, newRating.getTomato());
// }

// @Test
// void testEvaluateUpdate_Fresh() {
// int score = evaluateService.EvaluateUpdate("fresh");
// assertEquals(1, score);
// }

// @Test
// void testEvaluateUpdate_Rotten() {
// int score = evaluateService.EvaluateUpdate("rotten");
// assertEquals(-1, score);
// }

// @Test
// void testUpdateMovieRating_Fresh() {
// evaluateService.updateMovieRating(movieDetailEntity, "fresh");
// assertEquals(1, movieDetailEntity.getFreshCount());
// assertEquals(0, movieDetailEntity.getRottenCount());
// }

// @Test
// void testUpdateMovieRating_Rotten() {
// evaluateService.updateMovieRating(movieDetailEntity, "rotten");
// assertEquals(0, movieDetailEntity.getFreshCount());
// assertEquals(1, movieDetailEntity.getRottenCount());
// }

// @Test
// void testUpdateTomatoScore() {
// movieDetailEntity.setFreshCount(1);
// movieDetailEntity.setRottenCount(1);
// evaluateService.updateTomatoScore(movieDetailEntity);
// assertEquals(50.0, movieDetailEntity.getTomatoScore());
// }
// }