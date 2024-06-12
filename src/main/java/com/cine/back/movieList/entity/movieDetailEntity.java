package com.cine.back.movieList.entity;

import java.util.List;

import com.cine.back.movieList.dto.Credits;
import com.cine.back.movieList.dto.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class MovieDetailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieDetail_no")
    private long movieDetail;

    @JsonProperty("id")
    @Column(name = "movie_id")
    private int movie_id;

    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JsonProperty("release_date")
    @Column(length = 20)
    private String release_date;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("poster_path")
    @Column(length = 100)
    private String poster_path;
    
    @JsonProperty("genres")
    @Transient  // jpa가 매핑 못하게 만드는 어노테이션
    private List<Genre> genres;

    @JsonProperty("credits")
    @Transient
    private Credits credits;

}

