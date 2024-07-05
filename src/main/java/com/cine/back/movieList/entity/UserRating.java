package com.cine.back.movieList.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "movie_id")
    private int movieId;
    
    @NotNull
    @Column(name = "user_id")
    private String userId;
    
    @NotNull
    @Column(name = "rating")
    private String rating; // 'fresh' or 'rotten'
    
    @NotNull
    @Column(name = "tomato")
    private int tomato; // 평가 척도
    
    @Column(name = "deleted_Date")
    private LocalDateTime deletedDate;    // 평가 삭제 시점
    
    @Column(name = "check_Deleted")
    private boolean checkDeleted; // 삭제 유무 판단
    
    @Builder
    public UserRating(int movieId, String userId,
             String rating, int tomato, LocalDateTime deletedDate, boolean checkDeleted) {
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.tomato = tomato;
        this.deletedDate = deletedDate;
        this.checkDeleted = checkDeleted;
    }
}
