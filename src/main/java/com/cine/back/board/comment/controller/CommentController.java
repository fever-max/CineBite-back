package com.cine.back.board.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.info("[POST][/board/post/{}/comment] - 댓글 저장 {}", postNo, requestDto);
        CommentResponseDto responseDto = commentService.writeComment(postNo, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @GetMapping("/post/{postNo}/comment")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(Long postNo) {
        log.info("[GET][/board/post/{}/comment] - 댓글 조회", postNo);
        List<CommentResponseDto> commentResponses = commentService.getAllComments(postNo);
        return ResponseEntity.ok().body(commentResponses);
    }

    @Override
    @DeleteMapping("/post/{postNo}/comment/{commentNo}")
    public ResponseEntity<Long> deleteCommentById(Long postNo, Long commentNo) {
        log.info("[DELETE][/board/post/{}/comment/{}] - 댓글 삭제", postNo, commentNo);
        commentService.deleteComment(postNo, commentNo);
        return ResponseEntity.ok().body(postNo);
    }

    @Override
    @PatchMapping("/post/{postNo}/comment/{commentNo}")
    public ResponseEntity<Long> updateComment(Long postNo, Long commentNo,
            CommentRequestDto commentRequestDto) {
        log.info("[PATCH][/board/post/{}/comment/{}] - 댓글 수정", postNo, commentNo);
        CommentResponseDto commentResponseDto = commentService.modifyComment(commentNo, commentRequestDto);
        return ResponseEntity.ok().body(commentResponseDto.postNo());
    }

}
