package com.cine.back.movieList.response;

import java.util.List;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.entity.movieDetailEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MovieDTO {
    
    @JsonProperty("genres")
    private List<movieDetailEntity> genres;


}
