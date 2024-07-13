package com.cine.back.board.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Like", description = "Post Like API")
public interface LikeControllerDocs {

        @Operation(summary = "게시글 좋아요", description = "작성자의 아이디로 게시글을 좋아합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "좋아요 성공"),
                        @ApiResponse(responseCode = "404", description = "좋아요 저장 실패"),
                        @ApiResponse(responseCode = "500", description = "서버 내부 오류") })
        public ResponseEntity<String> toggleLike(@PathVariable("postNo") Long postNo,
                        @PathVariable("userId") String userId);

}