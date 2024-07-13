package com.cine.back.board.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.tag.dto.TagRequestDto;
import com.cine.back.board.tag.dto.TagResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.*;

@Tag(name = "Tag", description = "Tag API")
public interface TagControllerDocs {

    @Operation(summary = "최신 태그 조회", description = "최신 태그 5개를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그 조회 성공"),
            @ApiResponse(responseCode = "400", description = "태그 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<TagResponseDto>> getRecentTags();

    @Operation(summary = "인기 태그 조회", description = "빈도수가 돞은 태그 5개를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그 조회 성공"),
            @ApiResponse(responseCode = "400", description = "태그 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<TagResponseDto>> getPopularTags();

    @Operation(summary = "태그 조회", description = "태그 이름으로 태그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "태그 조회 성공"),
            @ApiResponse(responseCode = "400", description = "태그 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
    public ResponseEntity<List<PostResponseDto>> getPostsByTag(@Valid @RequestBody TagRequestDto requestDto);

}
