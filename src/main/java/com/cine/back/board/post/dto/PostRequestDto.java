package com.cine.back.board.post.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDto(
                @NotBlank(message = "[NotBlank] 게시물 제목") @Size(max = 500, message = "게시물 최대 길이 초과 (500자)") String postTitle,
                @NotBlank(message = "[NotBlank] 게시물 내용") @Size(max = 2000, message = "게시물 내용 최대 길이 초과 (3000자)") String postContent,
                @NotBlank(message = "[NotBlank] 사용자 ID ") String userId,
                List<String> tagNames) {
}
