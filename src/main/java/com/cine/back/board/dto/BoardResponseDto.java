package com.cine.back.board.dto;

import java.util.List;
import java.time.LocalDateTime;

public record BoardResponseDto(
        Long boardNo,
        String boardTitle,
        String boardContent,
        String userId,
        List<String> tagNames,
        int boardHitCount,
        int boardCommentCount,
        int boardLikeCount,
        String boardImgUrl,
        LocalDateTime boardCreatedDate)

{
    public static BoardResponseDto of(Long boardNo, String boardTitle, String boardContent, String userId,
            List<String> tagNames, int boardHitCount, int boardCommentCount,
            int boardLikeCount, String boardImgUrl, LocalDateTime boardCreatedDate) {
        return new BoardResponseDto(boardNo, boardTitle, boardContent, userId, tagNames, boardHitCount,
                boardCommentCount, boardLikeCount, boardImgUrl, boardCreatedDate);
    }
}
