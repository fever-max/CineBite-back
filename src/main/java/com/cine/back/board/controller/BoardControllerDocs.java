package com.cine.back.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.dto.BoardRequestDto;
import com.cine.back.board.dto.BoardResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Board", description = "Board API")
public interface BoardControllerDocs {

        // 글 저장
        @Operation(summary = "게시판 글 저장", description = "게시판에 글을 저장합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "글 저장 성공"),
                        @ApiResponse(responseCode = "400", description = "글 저장 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<Long> saveBoard(@RequestPart(value = "dto") BoardRequestDto boardDto,
                        @RequestPart(value = "file") MultipartFile imgFile) throws IOException;

        // 글 전체 조회
        @Operation(summary = "게시판 글 전체 조회", description = "게시판에 저장된 모든 글을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "글 전체 조회 성공"),
                        @ApiResponse(responseCode = "400", description = "글 전체 조회 실패") })
        ResponseEntity<List<BoardResponseDto>> getAllBoards();

        // 글 세부 조회
        @Operation(summary = "게시판 글 세부 조회", description = "게시판에 저장된 특정 글을 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "글 세부 조회 성공"),
                        @ApiResponse(responseCode = "400", description = "글 세부 조회 실패") })
        ResponseEntity<BoardResponseDto> getBoardById(@PathVariable(value = "no") Long boardNo);

        // 글 삭제
        @Operation(summary = "게시판 글 삭제", description = "게시판에서 글을 삭제합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "글 삭제 성공"),
                        @ApiResponse(responseCode = "400", description = "글 삭제 실패") })
        ResponseEntity<String> deleteBoard(@PathVariable(value = "no") Long boardNo) throws IOException;

        // 글 수정
        @Operation(summary = "게시판 글 수정", description = "게시판에 저장된 글을 수정합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "글 수정 성공"),
                        @ApiResponse(responseCode = "400", description = "글 수정 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        ResponseEntity<Long> updateBoard(@PathVariable(value = "no") Long boardNo,
                        @RequestPart(value = "dto") BoardRequestDto boardDto,
                        @RequestPart(value = "file") MultipartFile imgFile) throws IOException;
}
