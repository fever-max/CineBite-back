package com.cine.back.movieList.response;

import com.cine.back.movieList.dto.TrendMovie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendMovieResponse {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<TrendMovie> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

}
