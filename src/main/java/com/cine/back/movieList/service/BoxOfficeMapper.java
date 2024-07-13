package com.cine.back.movieList.service;

import com.cine.back.movieList.dto.WeeklyBoxOffices;
import com.cine.back.movieList.entity.BoxOfficeMovieEntity;

public class BoxOfficeMapper {

    // DTO를 엔티티로 변환
    public static BoxOfficeMovieEntity toEntity(WeeklyBoxOffices dto) {
        BoxOfficeMovieEntity entity = new BoxOfficeMovieEntity();
        entity.setMovieRank(dto.getMovieRank());
        entity.setRankInTen(dto.getRankInTen());
        entity.setRankOldAndNew(dto.getRankOldAndNew());
        entity.setMovieCd(dto.getMovieCd()); // `movieCd`는 DTO에서 엔티티로 그대로 매핑
        entity.setMovieNm(dto.getMovieNm());
        return entity;
    }

    public static WeeklyBoxOffices toDto(BoxOfficeMovieEntity entity) {
        WeeklyBoxOffices dto = new WeeklyBoxOffices();
        dto.setMovieId(0);
        dto.setMovieRank(entity.getMovieRank());
        dto.setRankInTen(entity.getRankInTen());
        dto.setRankOldAndNew(entity.getRankOldAndNew());
        dto.setMovieCd(entity.getMovieCd());
        dto.setMovieNm(entity.getMovieNm());
        dto.setPoster_path("");
        return dto;
    }
}
