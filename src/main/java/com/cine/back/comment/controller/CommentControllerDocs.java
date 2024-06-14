package com.cine.back.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;

import com.cine.back.comment.dto.CommentRequestDto;
import com.cine.back.comment.dto.CommentResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comment", description = "Comment API")
public interface CommentControllerDocs {

        // 댓글 저장
        @Operation(summary = "댓글 저장", description = "댓글을 저장합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 저장 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 저장 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<CommentResponseDto> saveComment(@PathVariable(value = "postNo") Long PostNo,
                        @RequestPart(value = "dto") CommentRequestDto requestDto);

        // 게시글 댓글 조회
        @Operation(summary = "댓글 조회", description = "게시판 No로 해당 댓글을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 조회 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 조회 실패") })
        public ResponseEntity<List<CommentResponseDto>> getAllComments(@PathVariable(value = "postNo") Long PostNo);

        // 댓글 삭제
        @Operation(summary = "댓글 삭제", description = "댓글 No로 댓글을 삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 삭제 실패") })
        public ResponseEntity<Long> deleteCommentById(@PathVariable(value = "postNo") Long postNo,
                        @PathVariable(value = "commentNo") Long commentNo);

        // 댓글 수정
        @Operation(summary = "댓글 수정", description = "댓글 No로 댓글을 수정합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
                        @ApiResponse(responseCode = "400", description = "댓글 수정 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<Long> updateComment(@PathVariable(value = "no") Long commentNo,
                        CommentRequestDto commentRequestDto);

}
