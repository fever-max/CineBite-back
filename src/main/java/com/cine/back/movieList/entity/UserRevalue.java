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
@Entity(name = "User_Revalue")
public class UserRevalue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revalueId;

    @NotNull
    @Column(name = "movie_id")
    private int movieId;
    
    @NotNull
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "deleted_Date")
    private LocalDateTime deletedDate;    // 평가 삭제 시점
    
    @Column(name = "check_Deleted")
    private boolean checkDeleted; // 삭제 유무 판단
    
    @Builder
    public UserRevalue(int movieId, String userId, LocalDateTime deletedDate, boolean checkDeleted) {
        this.movieId = movieId;
        this.userId = userId;
        this.deletedDate = deletedDate;
        this.checkDeleted = checkDeleted;
    }
}
