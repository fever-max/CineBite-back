package com.cine.back.board.post.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

import com.cine.back.board.comment.entity.CommentEntity;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_list")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long postNo;

    // 제목, 내용
    @NotNull
    @Column(name = "post_title", length = 500)
    private String postTitle;

    @NotNull
    @Column(name = "post_content", length = 3000)
    private String postContent;

    // 작성자
    @NotNull
    @Column(name = "user_id", length = 100)
    private String userId;

    // 조회수, 댓글수, 좋아요 수
    @Column(name = "hit_count")
    private int hitCount = 0;

    @Column(name = "comment_count")
    private int commentCount = 0;

    @Column(name = "like_count")
    private int likeCount = 0;

    // 이미지
    @Column(name = "imgUrl", length = 1000)
    private String imgUrl;

    // 작성일, 수정일
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Builder
    public PostEntity(String postTitle, String postContent, String userId, LocalDateTime createdDate,
            String imgUrl) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.userId = userId;
        this.createdDate = createdDate;
        this.imgUrl = imgUrl;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostTagMapEntity> postTagMappings;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntity;

}
