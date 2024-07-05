package com.cine.back.favorite.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFavorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;
    
    @NotNull
    @Column(nullable = false, name = "userid")
    private String userId;
    
    @NotNull
    @Column(nullable = false, name = "movieId")
    private int movieId;

    @JsonProperty("poster_path")
    @NotNull
    @Column(nullable = false, length = 100)
    private String posterPath;

    @JsonProperty("title")
    @NotNull
    @Column(nullable = false, name = "title")
    private String title;

    @Builder
    public UserFavorite(String userId, int movieId, String posterPath, String title) {
        this.userId = userId;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.title = title;
    }
}
