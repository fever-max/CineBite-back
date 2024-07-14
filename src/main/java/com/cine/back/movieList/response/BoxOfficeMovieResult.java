package com.cine.back.movieList.response;

import java.util.List;

import com.cine.back.movieList.dto.WeeklyBoxOffices;
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
    private List<WeeklyBoxOffices> weeklyBoxOfficeList;
}
