package com.cine.back.comment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.cine.back.comment.dto.CommentRequestDto;
import com.cine.back.comment.dto.CommentResponseDto;
import com.cine.back.comment.entity.CommentEntity;
import com.cine.back.post.entity.PostEntity;

@Component
public class CommentMapper {

    public CommentEntity toCommentEntity(PostEntity post, CommentRequestDto requestDto) {
        return CommentEntity.builder()
                .commentContent(requestDto.content())
                .userId(requestDto.userId())
                .createdDate(LocalDateTime.now())
                .post(post)
                .build();
    }

    public CommentResponseDto toResponseDto(CommentEntity commentEntity) {
        return CommentResponseDto.of(
                commentEntity.getPost().getPostNo(),
                commentEntity.getCommentNo(),
                commentEntity.getUserId(),
                commentEntity.getCommentContent(),
                commentEntity.getCreatedDate());
    }

}
