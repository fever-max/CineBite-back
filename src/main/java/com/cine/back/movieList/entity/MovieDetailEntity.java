package com.cine.back.movieList.entity;

import java.util.*;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "movie_details")
public class MovieDetailEntity {
    
    @Id
    @JsonProperty("id")
    @Column(name = "movie_id")
    private int movieId;

    @NotNull
    @JsonProperty("title")
    @Column(name = "title")
    private String title;
    
    @NotNull
    @JsonProperty("release_date")
    @Column(length = 20)
    private String releaseDate;
    
    @NotNull
    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;
    
    @NotNull
    @JsonProperty("poster_path")
    @Column(length = 100)
    private String posterPath;
    
    @NotNull
    @JsonProperty("popularity")
    @Column(length = 20)
    private String popularity;
    
    @NotNull
    @JsonProperty("runtime")
    @Column(length = 100)
    private String runtime;
    
    @NotNull
    @JsonProperty("genres")
    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    private List<Genre> genres;
    
    @NotNull
    @JsonProperty("credits")
    private Credits credits;
    
    @NotNull
    @Column(name = "fresh_count")
    private int freshCount;
    
    @NotNull
    @Column(name = "rotten_count")
    private int rottenCount;
    
    @NotNull
    @Column(name = "tomato_score")
    private double tomatoScore; // 평가 퍼센티지

    @Builder
    public MovieDetailEntity(int freshCount, int rottenCount, double tomatoScore) {
        this.freshCount = freshCount;
        this.rottenCount = rottenCount;
        this.tomatoScore = tomatoScore;
        
    }

}