package com.cine.back.board.like.entity;

import com.cine.back.board.post.entity.PostEntity;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.*;

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
