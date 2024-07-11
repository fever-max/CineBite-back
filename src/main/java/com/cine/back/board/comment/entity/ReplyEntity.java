package com.cine.back.board.comment.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.*;

@Entity
@Data
@Table(name = "reply_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_no")
    private Long replyNo;

    @NotNull
    @Column(name = "reply_content", length = 1000)
    private String replyContent;

    @NotNull
    @Column(name = "user_id", length = 100)
    private String userId;

    // 작성일, 수정일
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "comment_no")
    private CommentEntity comment;

    @Builder
    public ReplyEntity(String replyContent, String userId, LocalDateTime createdDate, CommentEntity comment) {
        this.replyContent = replyContent;
        this.userId = userId;
        this.createdDate = createdDate;
        this.comment = comment;
    }

}
