package com.cine.back.board.comment.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.dto.CommentResponseDto;
import com.cine.back.board.comment.entity.CommentEntity;
import com.cine.back.board.comment.repository.CommentRepository;
import com.cine.back.board.post.entity.PostEntity;
import com.cine.back.board.util.EntityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EntityUtil entityUtil;

    @Transactional
    public CommentResponseDto writeComment(Long postNo, CommentRequestDto requestDto) {
        try {
            PostEntity post = entityUtil.findPostById(postNo);
            entityUtil.updateCommentCount(postNo);
            CommentEntity commentEntity = commentMapper.toCommentEntity(post, requestDto);
            commentRepository.save(commentEntity);
            log.info("댓글 저장 성공 / post No: {}, comment No: {}", postNo, commentEntity.getCommentNo());
            CommentResponseDto responseDto = commentMapper.toResponseDto(commentEntity);
            return responseDto;
        } catch (NoSuchElementException e) {
            log.error("조회할 게시글이 없음: {}", e.getMessage());
            throw e;
        }

    }

    @Transactional
    public List<CommentResponseDto> getAllComments(Long postNo) {
        try {
            PostEntity post = entityUtil.findPostById(postNo);
            List<CommentEntity> commentEntities = commentRepository.findByPost_PostNo(post.getPostNo());
            List<CommentResponseDto> commentResponseDtos = commentMapper.toResponseDtos(commentEntities);
            log.info("댓글 조회 성공 / post No: {}, 총 댓글 수 {}개", postNo, commentResponseDtos.size());
            return commentResponseDtos;
        } catch (NoSuchElementException e) {
            log.error("조회할 게시글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteComment(Long postNo, Long commentNo) {
        try {
            PostEntity post = entityUtil.findPostById(postNo);
            entityUtil.deleteCommentCount(post.getPostNo());
            CommentEntity comment = entityUtil.findCommentById(commentNo);
            commentRepository.delete(comment);
            log.info("댓글 삭제 성공 / comment No: {}", commentNo);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @Transactional
    public CommentResponseDto modifyComment(Long commentNo, CommentRequestDto commentRequestDto) {
        try {
            CommentEntity commentEntity = entityUtil.findCommentById(commentNo);
            CommentEntity updateCommentEntity = commentMapper.updateCommentEntity(commentEntity, commentRequestDto);
            CommentResponseDto commentResponseDto = commentMapper.toResponseDto(updateCommentEntity);
            log.info("댓글 수정 성공 / comment No: {}", commentNo);
            return commentResponseDto;
        } catch (NoSuchElementException e) {
            log.error("수정할 댓글이 없음: {}", e.getMessage());
            throw e;
        }

    }

}
