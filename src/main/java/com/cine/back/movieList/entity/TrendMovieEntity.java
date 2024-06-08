package com.cine.back.movieList.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendMovieEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trend_movie_no")
    private int id;

    @JsonProperty("backdrop_path")
    @Column(length = 100)
    private String backdropPath;

    @JsonProperty("original_title")
    @Column(length = 100)
    private String originalTitle;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("poster_path")
    @Column(length = 100)
    private String posterPath;

    @JsonProperty("adult")
    @Column
    private boolean adult;

    @JsonProperty("title")
    @Column(length = 100)
    private String title;

    @JsonProperty("original_language")
    @Column(length = 10)
    private String originalLanguage;

    @JsonProperty("popularity")
    @Column
    private double popularity;

    @JsonProperty("release_date")
    @Column(length = 20)
    private String releaseDate;

    @JsonProperty("video")
    @Column
    private boolean video;

    @JsonProperty("vote_average")
    @Column
    private double voteAverage;

    @JsonProperty("vote_count")
    @Column
    private int voteCount;
}
