package com.cine.back.comment.dto;

import lombok.Getter;

public record CommentRequestDto(String userId,
        String content) {

}
