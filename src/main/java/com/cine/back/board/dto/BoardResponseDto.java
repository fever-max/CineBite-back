package com.cine.back.board.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

import java.time.LocalDateTime;

@Data
public class BoardResponseDto {

    private Long boardNo;

    private String boardTitle;

    private String boardContent;

    private String userEmail;

    private List<String> tagNames;

    private int boardHitCount;

    private int boardCommentCount;

    private int boardLikeCount;

    private String boardImgUrl;

    private LocalDateTime boardCreatedDate;

    @Builder
    public BoardResponseDto(Long boardNo, String boardTitle, String boardContent, String userEmail,
            List<String> tagNames,
            int boardHitCount, int boardCommentCount, int boardLikeCount, String boardImgUrl,
            LocalDateTime boardCreatedDate) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.userEmail = userEmail;
        this.tagNames = tagNames;
        this.boardHitCount = boardHitCount;
        this.boardCommentCount = boardCommentCount;
        this.boardLikeCount = boardLikeCount;
        this.boardImgUrl = boardImgUrl;
        this.boardCreatedDate = boardCreatedDate;
    }

}
