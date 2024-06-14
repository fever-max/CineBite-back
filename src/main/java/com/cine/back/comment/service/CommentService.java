package com.cine.back.comment.service;

import java.util.List;
import java.util.ArrayList;

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
        PostEntity post = findPostById(postNo);
        CommentEntity commentEntity = commentMapper.toCommentEntity(post, requestDto);
        commentRepository.save(commentEntity);
        log.info("댓글 저장 성공 / post No: {}, comment No: {}", postNo, commentEntity.getCommentNo());
        CommentResponseDto responseDto = commentMapper.toResponseDto(commentEntity);
        return responseDto;
    }

    public List<CommentResponseDto> getAllComments(Long postNo) {
        PostEntity post = findPostById(postNo);
        List<CommentEntity> commentEntities = commentRepository.findByPost_PostNo(post.getPostNo());
        List<CommentResponseDto> commentResponseDtos = commentMapper.toResponseDtos(commentEntities);
        log.info("댓글 조회 성공 / post No: {}, 총 댓글 수 {}개", postNo, commentResponseDtos.size());
        return commentResponseDtos;
    }

    public void deleteComment(Long commentNo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }

    private PostEntity findPostById(Long postNo) {
        return postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }

    private CommentEntity findCommentById(Long commentNo) {
        return commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

}
