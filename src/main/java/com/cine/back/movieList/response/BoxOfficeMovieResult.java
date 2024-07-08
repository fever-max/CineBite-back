package com.cine.back.movieList.response;

import java.util.List;

import com.cine.back.movieList.entity.BoxOfficeMovieEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BoxOfficeMovieResult {
    @JsonProperty("boxofficeType")
    private String boxOfficeType;

    @JsonProperty("showRange")
    private String showRange;

    @JsonProperty("yearWeekTime")
    private String yearWeekTime;

    @JsonProperty("weeklyBoxOfficeList")
    private List<BoxOfficeMovieEntity> weeklyBoxOfficeList;
}
