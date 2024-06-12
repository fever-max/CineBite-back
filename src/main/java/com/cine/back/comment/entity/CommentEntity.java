package com.cine.back.comment.entity;

import java.time.LocalDateTime;

import com.cine.back.post.entity.PostEntity;

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
@Table(name = "comment_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    @NotNull
    @Column(name = "comment_content", length = 1000)
    private String commentContent;

    @NotNull
    @Column(name = "userId", length = 100)
    private String userId;

    // 작성일, 수정일
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "post_no")
    private PostEntity post;

    @Builder
    public CommentEntity(String commentContent, String postContent, String userId,
            LocalDateTime createdDate, PostEntity post) {
        this.commentContent = commentContent;
        this.userId = userId;
        this.createdDate = createdDate;
        this.post = post;
    }

}
