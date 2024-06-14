package com.cine.back.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.comment.dto.CommentRequestDto;
import com.cine.back.comment.dto.CommentResponseDto;
import com.cine.back.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @Override
    @PostMapping("/{postNo}/write")
    public ResponseEntity<CommentResponseDto> saveComment(Long postNo, CommentRequestDto requestDto) {
        log.info("댓글 저장 컨트롤러, 게시글 NO: {}", postNo);
        CommentResponseDto responseDto = commentService.writeComment(postNo, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @GetMapping("/{postNo}")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(Long postNo) {
        log.info("댓글 조회 컨트롤러, 게시글 NO: {}", postNo);
        List<CommentResponseDto> commentResponses = commentService.getAllComments(postNo);
        return ResponseEntity.ok().body(commentResponses);
    }

    @Override
    @DeleteMapping("/{postNo}/delete/{commentNo}")
    public ResponseEntity<Long> deleteCommentById(Long postNo, Long commentNo) {
        log.info("특정 댓글 삭제 컨트롤러, Comment No: {}", commentNo);
        commentService.deleteComment(commentNo);
        return ResponseEntity.ok().body(postNo);
    }

    @Override
    public ResponseEntity<Long> updateComment(Long commentNo, CommentRequestDto commentRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

}
