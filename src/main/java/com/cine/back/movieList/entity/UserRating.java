package com.cine.back.movieList.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "User_Rating")
public class UserRating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MovieDetailEntity movieDetailEntity;

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "rating")
    private String rating; // 'fresh' or 'rotten'

    @Column(name = "tomato")
    private int tomato; // 평가 척도

}
