package com.cine.back.board.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;

import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.dto.ReplyResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comment reply", description = "Comment reply API")
public interface ReplyControllerDocs {

        // 댓글 저장
        @Operation(summary = "대댓글 저장", description = "대댓글을 저장합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 저장 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 저장 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<ReplyResponseDto> saveReply(@PathVariable(value = "commentNo") Long commentNo,
                        @RequestBody CommentRequestDto requestDto);

        // 게시글 댓글 조회
        @Operation(summary = "대댓글 조회", description = "댓글 No로 해당 대댓글을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 조회 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 조회 실패") })
        public ResponseEntity<List<ReplyResponseDto>> getAllReplies(
                        @PathVariable(value = "commentNo") Long commentNo);

        // 댓글 삭제
        @Operation(summary = "대댓글 삭제", description = "대댓글 No로 댓글을 삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 삭제 실패") })
        public ResponseEntity<Long> deleteReplyById(@PathVariable(value = "commentNo") Long commentNo,
                        @PathVariable(value = "replyNo") Long replyNo);

        // 댓글 수정
        @Operation(summary = "댓글 수정", description = "대댓글 No로 댓글을 수정합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 수정 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<Long> updateComment(@PathVariable(value = "commentNo") Long commentNo,
                        @PathVariable(value = "replyNo") Long replyNo,
                        @RequestBody CommentRequestDto commentRequestDto);

}
