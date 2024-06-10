package com.cine.back.board.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_list")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    // 제목, 내용
    @NotNull
    @Column(name = "board_title", length = 500)
    private String boardTitle;

    @NotNull
    @Column(name = "board_content", length = 3000)
    private String boardContent;

    // 작성자
    @NotNull
    @Column(name = "user_email", length = 100)
    private String userEmail;

    // 조회수, 댓글수, 좋아요 수
    @Column(name = "board_hit_count")
    private int boardHitCount = 0;

    @Column(name = "board_comment_count")
    private int boardCommentCount = 0;

    @Column(name = "board_like_count")
    private int boardLikeCount = 0;

    // 이미지
    @Column(name = "board_imgUrl", length = 1000)
    private String boardImgUrl;

    // 작성일, 수정일
    @Column(name = "board_createdDate")
    private LocalDateTime boardCreatedDate;

    @Column(name = "board_updateDate")
    private LocalDateTime boardUpdateDate;

    @Builder
    public BoardEntity(String boardTitle, String boardContent, String userEmail, LocalDateTime boardCreatedDate,
            String boardImgUrl) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.userEmail = userEmail;
        this.boardCreatedDate = boardCreatedDate;
        this.boardImgUrl = boardImgUrl;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardTagMapEntity> boardTagMappings;

}
