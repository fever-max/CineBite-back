package com.cine.back.board.tag.dto;

import jakarta.validation.constraints.NotBlank;

public record TagRequestDto(
        @NotBlank(message = "[NotBlank] 게시물 태그") String tagName) {
}
