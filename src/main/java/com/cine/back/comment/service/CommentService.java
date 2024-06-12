package com.cine.back.comment.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.cine.back.comment.dto.CommentRequestDto;
import com.cine.back.comment.dto.CommentResponseDto;
import com.cine.back.comment.entity.CommentEntity;
import com.cine.back.comment.repository.CommentRepository;
import com.cine.back.post.entity.PostEntity;
import com.cine.back.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentResponseDto writeComment(Long postNo, CommentRequestDto requestDto) {
        PostEntity post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        CommentEntity commentEntity = commentMapper.toCommentEntity(post, requestDto);
        commentRepository.save(commentEntity);
        log.info("댓글 저장 성공 / post No: {}, comment No: {}", postNo, commentEntity.getCommentNo());
        CommentResponseDto responseDto = commentMapper.toResponseDto(commentEntity);
        return responseDto;
    }

}
