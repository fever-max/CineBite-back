package com.cine.back.board.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
                @NotBlank(message = "[NotBlank] 사용자 ID ") String userId,
                @NotBlank(message = "[NotBlank] 댓글 내용") @Size(max = 1000, message = "댓글 길이 초과(1000자)") String content) {
}