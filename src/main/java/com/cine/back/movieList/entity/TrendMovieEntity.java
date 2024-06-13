package com.cine.back.movieList.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 사용하지 않을 api 필드 무시
@Entity
@Table(name = "TrendMovie")
public class TrendMovieEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trend_movie_no")
    private int trend_movie_no;

    @JsonProperty("id")
    @Column(name = "movie_id")  // 영화 번호
    private String movie_id;

    @JsonProperty("poster_path")
    @Column(length = 100)
    private String posterPath;

    @JsonProperty("title")
    @Column(length = 100)
    private String title;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("release_date")
    @Column(length = 20)
    private String releaseDate;

    @JsonProperty("popularity")
    @Column(length = 100)
    private String popularity;
}
