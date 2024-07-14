package com.cine.back.movieList.dto;

import lombok.Data;

@Data
public class WeeklyBoxOffices {
    private int movieId; // tmdb movie id
    private String movieRank;// 해당일자의 박스오피스 순위 출력
    private String rankInTen;// 전일대비 순위의 증감분
    private String rankOldAndNew;// 랭킹에 신규진입여부 출력
    private String movieCd;// 영진위 movie id
    private String movieNm;
    private String poster_path;
}
