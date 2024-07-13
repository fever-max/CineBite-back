package com.cine.back.board.search.dto;

import jakarta.validation.constraints.NotBlank;

public record PostContentDto(@NotBlank(message = "[NotBlank] 게시물 내용") String postContent) {

}
