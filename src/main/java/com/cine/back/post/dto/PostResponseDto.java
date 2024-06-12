package com.cine.back.post.dto;

import java.util.List;
import java.time.LocalDateTime;

public record PostResponseDto(
                Long postNo,
                String postTitle,
                String postContent,
                String userId,
                List<String> tagNames,
                int hitCount,
                int commentCount,
                int likeCount,
                String imgUrl,
                LocalDateTime createdDate)

{
        public static PostResponseDto of(Long postNo, String postTitle, String postContent, String userId,
                        List<String> tagNames, int hitCount, int commentCount,
                        int likeCount, String imgUrl, LocalDateTime createdDate) {
                return new PostResponseDto(postNo, postTitle, postContent, userId, tagNames, hitCount,
                                commentCount, likeCount, imgUrl, createdDate);
        }
}
