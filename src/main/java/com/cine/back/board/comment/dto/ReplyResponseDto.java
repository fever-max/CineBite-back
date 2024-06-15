package com.cine.back.board.comment.dto;

import java.time.LocalDateTime;

public record ReplyResponseDto(
        Long commentNo,
        Long replyNo,
        String userId,
        String content,
        LocalDateTime date) {
    public static ReplyResponseDto of(Long commentNo, Long replyNo, String userId, String content, LocalDateTime date) {
        return new ReplyResponseDto(commentNo, replyNo, userId, content, date);
    }

}
