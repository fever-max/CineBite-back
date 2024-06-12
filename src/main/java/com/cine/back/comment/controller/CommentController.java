package com.cine.back.comment.controller;

import org.springframework.http.ResponseEntity;
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
    @PostMapping("/{no}/write")
    public ResponseEntity<CommentResponseDto> saveComment(Long postNo, CommentRequestDto requestDto) {
        log.info("댓글 저장 컨트롤러, 게시글 NO: {}", postNo);
        CommentResponseDto responseDto = commentService.writeComment(postNo, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<Long> getAllComments(Long PostNo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllComments'");
    }

    @Override
    public ResponseEntity<Long> deleteCommentById(Long CommentNo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCommentById'");
    }

    @Override
    public ResponseEntity<Long> updateComment(Long CommentNo, CommentRequestDto commentRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

}
