package com.cine.back.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cine.back.post.dto.PostRequestDto;
import com.cine.back.post.dto.PostResponseDto;
import com.cine.back.post.entity.PostEntity;

@Component
public class PostMapper {

    public PostEntity toPostEntity(PostRequestDto boardDto, String boardImgUrl) {
        return PostEntity.builder()
                .postTitle(boardDto.postTitle())
                .postContent(boardDto.postContent())
                .userId(boardDto.userId())
                .createdDate(LocalDateTime.now())
                .imgUrl(boardImgUrl)
                .build();
    }

    public PostResponseDto toResponseDto(PostEntity board, List<String> tagNames) {
        return PostResponseDto.of(
                board.getPostNo(),
                board.getPostTitle(),
                board.getPostContent(),
                board.getUserId(),
                tagNames,
                board.getHitCount(),
                board.getCommentCount(),
                board.getLikeCount(),
                board.getImgUrl(),
                board.getCreatedDate());
    }

    public PostEntity updatePostEntity(PostEntity boardEntity, PostRequestDto boardDto, String boardImgUrl) {
        boardEntity.setPostTitle(boardDto.postTitle());
        boardEntity.setPostContent(boardDto.postContent());
        boardEntity.setUpdateDate(LocalDateTime.now());
        boardEntity.setImgUrl(boardImgUrl);
        return boardEntity;
    }
}
