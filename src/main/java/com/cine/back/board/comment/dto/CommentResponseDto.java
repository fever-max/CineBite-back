package com.cine.back.board.comment.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long postNo,
        Long commentNo,
        String userId,
        String content,
        LocalDateTime createDate,
        LocalDateTime updateDate) {

    public static CommentResponseDto of(Long postNo, Long commentNo, String userId, String content,
            LocalDateTime createDate, LocalDateTime updateDate) {
        return new CommentResponseDto(postNo, commentNo, userId, content, createDate, updateDate);
    }
}
