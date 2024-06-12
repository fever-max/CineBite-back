package com.cine.back.post.dto;

import java.util.List;

public record PostRequestDto(
                String postTitle,
                String postContent,
                String userId,
                List<String> tagNames) {
}
