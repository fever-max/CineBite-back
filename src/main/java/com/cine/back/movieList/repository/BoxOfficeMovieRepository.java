package com.cine.back.movieList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.movieList.entity.BoxOfficeMovieEntity;

public interface BoxOfficeMovieRepository extends JpaRepository<BoxOfficeMovieEntity, Integer> {
    Optional<BoxOfficeMovieEntity> findByMovieCd(String movieId);

    List<BoxOfficeMovieEntity> findAllByOrderByMovieRankAsc();

    // @Query("SELECT b.movieCd, b.movieRank, b.rankInTen, b.rankOldAndNew,
    // b.movieNm, m.movieId, m.posterPath FROM weekly_box_office b JOIN
    // movie_details m ON b.movieNm = m.title ORDER BY b.movieRank ASC")
    // List<WeeklyBoxOffices> findWeeklyBoxOffices();

}
