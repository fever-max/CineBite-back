package com.cine.back.board.search.controller;

import org.springframework.http.ResponseEntity;

import com.cine.back.board.post.dto.PostRequestDto;
import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.search.dto.PostContentDto;
import com.cine.back.board.search.dto.PostTitleDto;
import com.cine.back.board.search.dto.PostUserIdDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.util.*;

public interface PostSearchControllerDocs {

    // 글 제목으로 검색
    public ResponseEntity<List<PostResponseDto>> getPostsByTitle(@Valid @RequestBody PostTitleDto postTitleDto);

    // 글 내용으로 검색
    public ResponseEntity<List<PostResponseDto>> getPostsByContent(@Valid @RequestBody PostContentDto postContent);

    // 작성자로 검색
    public ResponseEntity<List<PostResponseDto>> findByUser(@Valid @RequestBody PostUserIdDto postUserId);

}
