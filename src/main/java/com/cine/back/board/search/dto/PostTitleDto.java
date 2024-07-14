package com.cine.back.board.search.dto;

import jakarta.validation.constraints.NotBlank;

public record PostTitleDto(
        @NotBlank(message = "[NotBlank] 게시물 제목") String postTitle) {

}
