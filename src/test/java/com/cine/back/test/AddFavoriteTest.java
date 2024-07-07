// package com.cine.back.test;

// import static org.mockito.ArgumentMatchers.anyInt;

// import com.cine.back.favorite.dto.FavoriteRequestDto;
// import com.cine.back.movieList.entity.MovieDetailEntity;

// public class AddFavoriteTest() {
//     // given
//     FavoriteRequestDto favoriteDto = new FavoriteRequestDto("user1", 123);
//     MovieDetailEntity movieDetail = new MovieDetailEntity(5, 3, 62.5);
//     movieDetail.setMovieId(123);
//     movieDetail.setPosterPath("/path/to/poster");
//     movieDetail.setTitle("Test Movie");

//     when(movieConfig.fetchMovieDetails(anyInt())).thenReturn(movieDetail);
//     when(userFavoriteRepository.save(any(UserFavorite.class))).thenAnswer(i -> i.getArguments()[0]);

//     // when
//     Optional<FavoriteResponseDto> response = userFavoriteService.addFavorite(favoriteDto);

//     // then
//     assertTrue(response.isPresent());
//     assertEquals(123, response.get().movieId());
//     assertEquals(62.5, response.get().tomatoScore(), 0.01);

// }
