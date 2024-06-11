package com.cine.back.board.dto;

import java.util.List;

public record BoardRequestDto(
        String boardTitle,
        String boardContent,
        String userId,
        List<String> tagNames) {
}
