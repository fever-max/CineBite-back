package com.cine.back.board.comment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cine.back.board.comment.dto.ReplyResponseDto;
import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.dto.CommentResponseDto;
import com.cine.back.board.comment.entity.CommentEntity;
import com.cine.back.board.comment.entity.ReplyEntity;
import com.cine.back.board.post.entity.PostEntity;

import java.util.stream.Collectors;

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

    public ReplyEntity toReplyEntity(CommentEntity comment, CommentRequestDto requestDto) {
        return ReplyEntity.builder()
                .replyContent(requestDto.content())
                .userId(requestDto.userId())
                .createdDate(LocalDateTime.now())
                .comment(comment)
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

    public List<CommentResponseDto> toResponseDtos(List<CommentEntity> commentEntities) {
        return commentEntities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public CommentEntity updateCommentEntity(CommentEntity commentEntity, CommentRequestDto commentRequestDto) {
        commentEntity.setCommentContent(commentRequestDto.content());
        commentEntity.setUpdateDate(LocalDateTime.now());
        return commentEntity;
    }

    public ReplyResponseDto toReplyResponseDto(ReplyEntity replyEntity) {
        return ReplyResponseDto.of(
                replyEntity.getComment().getCommentNo(),
                replyEntity.getReplyNo(),
                replyEntity.getUserId(),
                replyEntity.getReplyContent(),
                replyEntity.getCreatedDate());
    }

    public List<ReplyResponseDto> toReplyResponseDtos(List<ReplyEntity> replyEntities) {
        return replyEntities.stream()
                .map(this::toReplyResponseDto)
                .collect(Collectors.toList());
    }

    public ReplyEntity updateReplyEntity(ReplyEntity reply, CommentRequestDto commentRequestDto) {
        reply.setReplyContent(commentRequestDto.content());
        reply.setUpdateDate(LocalDateTime.now());
        return reply;
    }

}
