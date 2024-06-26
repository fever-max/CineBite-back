package com.cine.back.movieList.entity;

import java.util.List;

import com.cine.back.movieList.dto.Credits;
import com.cine.back.movieList.dto.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "movie_details")
public class MovieDetailEntity {

    @Id
    @JsonProperty("id")
    @Column(name = "movie_id")
    private int movieId;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("release_date")
    @Column(length = 20)
    private String releaseDate;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("poster_path")
    @Column(length = 100)
    private String posterPath;

    @JsonProperty("popularity")
    @Column(length = 20)
    private String popularity;

    @JsonProperty("runtime")
    @Column(length = 100)
    private String runtime;

    @JsonProperty("genres")
    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    private List<Genre> genres;

    @JsonProperty("credits")
    private Credits credits;

    // @Column(name = "fresh_count")
    // private int freshCount;

    // @Column(name = "rotten_count")
    // private int rottenCount;

    // @Column(name = "tomato_score")
    // private double tomatoScore; // 평가 퍼센티지

}