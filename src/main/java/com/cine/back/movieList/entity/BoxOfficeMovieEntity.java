package com.cine.back.movieList.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "weekly_box_office")
public class BoxOfficeMovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boxOfficeMovieId;

    @JsonProperty("rank")
    @Column(name = "movie_rank")
    private String movieRank;// 해당일자의 박스오피스 순위 출력

    @JsonProperty("rankInten")
    @Column(name = "rank_in_ten")
    private String rankInTen;// 전일대비 순위의 증감분

    @JsonProperty("rankOldAndNew")
    @Column(name = "rank_old_and_new")
    private String rankOldAndNew;// 랭킹에 신규진입여부 출력

    @JsonProperty("movieCd")
    @Column(name = "movie_cd")
    private String movieCd;

    @JsonProperty("movieNm")
    @Column(name = "movie_nm")
    private String movieNm;
}
