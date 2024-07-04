package com.cine.back.board.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.board.comment.dto.ReplyResponseDto;
import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class ReplyController implements ReplyControllerDocs {

    private final ReplyService replyService;

    @Override
    @PostMapping("/comment/{commentNo}/reply")
    public ResponseEntity<ReplyResponseDto> saveReply(Long commentNo, CommentRequestDto requestDto) {
        log.info("[POST][/board/comment/{}/reply] - 대댓글 저장 {}", commentNo, requestDto);
        ReplyResponseDto responseDto = replyService.writeReply(commentNo, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @GetMapping("/comment/{commentNo}/reply")
    public ResponseEntity<List<ReplyResponseDto>> getAllReplies(Long commentNo) {
        log.info("[GET][/board/comment/{}/reply] - 대댓글 조회", commentNo);
        List<ReplyResponseDto> replyResponses = replyService.getAllReplies(commentNo);
        return ResponseEntity.ok().body(replyResponses);
    }

    @Override
    @DeleteMapping("/comment/{commentNo}/reply/{replyNo}")
    public ResponseEntity<Long> deleteReplyById(Long commentNo, Long replyNo) {
        log.info("[DELETE][/board/comment/{}/reply/{}] - 대댓글 삭제", commentNo, replyNo);
        replyService.deleteReply(commentNo, replyNo);
        return ResponseEntity.ok().body(commentNo);
    }

    @Override
    @PatchMapping("/comment/{commentNo}/reply/{replyNo}")
    public ResponseEntity<Long> updateReply(Long commentNo, Long replyNo, CommentRequestDto commentRequestDto) {
        log.info("[PATCH][/board/comment/{}/reply/{}] - 대댓글 수정", commentNo, replyNo);
        ReplyResponseDto replyResponseDto = replyService.modifyReply(replyNo, commentRequestDto);
        return ResponseEntity.ok().body(replyResponseDto.commentNo());
    }

}
