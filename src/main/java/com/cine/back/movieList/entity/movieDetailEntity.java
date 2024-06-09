package com.cine.back.movieList.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class movieDetailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieDetail_no")
    private long movieDetail;
    
    @JsonProperty("movie_id")
    @Column(name = "movie_id")
    private int movie_id;

    @JsonProperty("title")
    @Column(name = "title ")
    private String title;

    @JsonProperty("genre")
    @Column
    private String genrename;  // id, name

    @JsonProperty("release_date")
    @Column(length = 20)
    private String release_date;

    @JsonProperty("tagline")
    @Column(length = 100)
    private String tagline;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("poster_path")
    @Column(length = 100)
    private String poster_path;
}
