package com.cine.back.board.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.board.post.dto.PostRequestDto;
import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.search.dto.PostContentDto;
import com.cine.back.board.search.dto.PostTitleDto;
import com.cine.back.board.search.dto.PostUserIdDto;
import com.cine.back.board.search.service.PostSearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class PostSearchController implements PostSearchControllerDocs {

    private final PostSearchService searchService;

    @Override
    @PostMapping("/post/title")
    public ResponseEntity<List<PostResponseDto>> getPostsByTitle(@RequestBody PostTitleDto postTitleDto) {
        log.info("[POST][/search/post/title] - 게시글 제목 조회: {}", postTitleDto);
        List<PostResponseDto> postResponseDtos = searchService.getPostsByTitle(postTitleDto.postTitle());
        return ResponseEntity.ok().body(postResponseDtos);
    }

    @Override
    @PostMapping("/post/content")
    public ResponseEntity<List<PostResponseDto>> getPostsByContent(@RequestBody PostContentDto postContentDto) {
        log.info("[POST][/search/post/content] - 게시글 내용 조회: {}", postContentDto.postContent());
        List<PostResponseDto> postResponseDtos = searchService.getPostsByContent(postContentDto.postContent());
        return ResponseEntity.ok().body(postResponseDtos);
    }

    @Override
    @PostMapping("/post/userId")
    public ResponseEntity<List<PostResponseDto>> findByUser(@RequestBody PostUserIdDto postUserIdDto) {
        log.info("[POST][/search/post/userId] - 게시글 내용 조회: {}", postUserIdDto.userId());
        List<PostResponseDto> postResponseDtos = searchService.getPostsByUser(postUserIdDto.userId());
        return ResponseEntity.ok().body(postResponseDtos);
    }

}
