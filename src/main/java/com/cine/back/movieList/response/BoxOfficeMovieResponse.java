package com.cine.back.movieList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BoxOfficeMovieResponse {
    @JsonProperty("boxOfficeResult")
    private BoxOfficeMovieResult results;
}
