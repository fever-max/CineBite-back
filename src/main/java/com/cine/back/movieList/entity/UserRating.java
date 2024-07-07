package com.cine.back.movieList.entity;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity(name = "User_Rating")
public class UserRating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @NotNull
    @Column(nullable = false, name = "movie_id")
    private int movieId;
    
    @NotNull
    @Column(nullable = false, name = "user_id")
    private String userId;
    
    @NotNull
    @Column(nullable = false, name = "rating")
    private String rating; // 'fresh' or 'rotten'
    
    @NotNull
    @Column(nullable = false, name = "tomato")
    private int tomato; // 평가 척도
    
    @Builder
    public UserRating(int movieId, String userId, String rating, int tomato) {
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.tomato = tomato;
    }
}
