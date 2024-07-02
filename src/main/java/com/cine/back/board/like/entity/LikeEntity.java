package com.cine.back.board.like.entity;

import java.time.LocalDateTime;

import com.cine.back.board.post.entity.PostEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long LikeNo;

    @NotNull
    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "post_no")
    private PostEntity post;

    @Builder
    public LikeEntity(String userId, LocalDateTime createdDate, PostEntity postEntity) {
        this.userId = userId;
        this.createdDate = createdDate;
        this.post = postEntity;
    }

}
