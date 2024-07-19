package com.cine.back.board.search.controller;

import org.springframework.http.ResponseEntity;

import com.cine.back.board.post.dto.PostRequestDto;
import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.search.dto.PostContentDto;
import com.cine.back.board.search.dto.PostTitleDto;
import com.cine.back.board.search.dto.PostUserIdDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.*;

@Tag(name = "Post Search", description = "Post Search API")
public interface PostSearchControllerDocs {

    // 글 제목으로 검색
    @Operation(summary = "게시판 제목 조회", description = "제목으로 게시판을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "글 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<PostResponseDto>> getPostsByTitle(@Valid @RequestBody PostTitleDto postTitleDto);

    // 글 내용으로 검색
    @Operation(summary = "게시판 내용 조회", description = "내용으로 게시판을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "글 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<PostResponseDto>> getPostsByContent(@Valid @RequestBody PostContentDto postContent);

    // 작성자로 검색
    @Operation(summary = "게시판 작성자 조회", description = "작성자로 게시판을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "글 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<PostResponseDto>> findByUser(@Valid @RequestBody PostUserIdDto postUserId);

}
