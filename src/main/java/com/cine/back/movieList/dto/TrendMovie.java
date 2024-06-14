package com.cine.back.movieList.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendMovie {

    @JsonProperty("id")
    private int movieId;
    
    private String posterPath;
    private String title;
    private String overview;
    private String releaseDate;
    private String popularity;
}
