// TrendMovieResponse.java
package com.cine.back.movieList.response;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class TrendMovieResponse {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<TrendMovieEntity> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

}
