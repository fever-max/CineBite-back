package com.cine.back.board.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.dto.CommentResponseDto;
import com.cine.back.board.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    @Override
    @PostMapping("/post/{postNo}/comment")
    public ResponseEntity<CommentResponseDto> saveComment(Long postNo, CommentRequestDto requestDto) {
        log.info("댓글 저장 컨트롤러, 게시글 NO: {}", postNo);
        CommentResponseDto responseDto = commentService.writeComment(postNo, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @GetMapping("/post/{postNo}/comment")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(Long postNo) {
        log.info("댓글 조회 컨트롤러, 게시글 NO: {}", postNo);
        List<CommentResponseDto> commentResponses = commentService.getAllComments(postNo);
        return ResponseEntity.ok().body(commentResponses);
    }

    @Override
    @DeleteMapping("/post/{postNo}/comment/{commentNo}")
    public ResponseEntity<Long> deleteCommentById(Long postNo, Long commentNo) {
        log.info("특정 댓글 삭제 컨트롤러, Comment No: {}", commentNo);
        commentService.deleteComment(postNo, commentNo);
        return ResponseEntity.ok().body(postNo);
    }

    @Override
    @PutMapping("/post/{postNo}/comment/{commentNo}")
    public ResponseEntity<Long> updateComment(Long postNo, Long commentNo,
            CommentRequestDto commentRequestDto) {
        log.info("특정 댓글 수정 컨트롤러, Comment No: {}", commentNo);
        CommentResponseDto commentResponseDto = commentService.modifyComment(commentNo, commentRequestDto);
        return ResponseEntity.ok().body(commentResponseDto.postNo());
    }

}
