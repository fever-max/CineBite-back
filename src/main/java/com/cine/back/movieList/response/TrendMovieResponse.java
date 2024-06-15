// TrendMovieResponse.java
package com.cine.back.movieList.response;

import com.cine.back.movieList.dto.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import java.util.List;

@Getter
public class TrendMovieResponse {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<Movie> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

}
