package com.cine.back.favorite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class UserFavorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;
    
    @Column(name = "userid")
    private String userId;
    
    @Column(name = "movieId")
    private int movieId;

    @Builder
    public UserFavorite(String userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
