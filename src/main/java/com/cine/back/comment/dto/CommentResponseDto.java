package com.cine.back.comment.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long postNo,
        Long commentNo,
        String userId,
        String content,
        LocalDateTime date) {
    public static CommentResponseDto of(Long postNo, Long commentNo, String userId, String content,
            LocalDateTime date) {
        return new CommentResponseDto(postNo, commentNo, userId, content, date);
    }
}
