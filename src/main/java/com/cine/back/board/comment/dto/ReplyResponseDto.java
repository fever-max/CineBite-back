package com.cine.back.board.comment.dto;

import java.time.LocalDateTime;

public record ReplyResponseDto(
        Long commentNo,
        Long replyNo,
        String userId,
        String content,
        LocalDateTime createDate,
        LocalDateTime updateDate) {
    public static ReplyResponseDto of(Long commentNo, Long replyNo, String userId, String content,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        return new ReplyResponseDto(commentNo, replyNo, userId, content, createDate, updateDate);
    }

}
