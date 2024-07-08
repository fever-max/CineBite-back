package com.cine.back.board.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.tag.dto.TagRequestDto;
import com.cine.back.board.tag.dto.TagResponseDto;
import com.cine.back.board.tag.service.TagSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController implements TagControllerDocs {

    private final TagSearchService tagSearchService;

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<TagResponseDto>> getRecentTags() {
        log.info("[GET][/tag/list] - 최근 태그 조회");
        List<TagResponseDto> tagResponseDtos = tagSearchService.getRecentTags();
        return ResponseEntity.ok().body(tagResponseDtos);
    }

    @Override
    @GetMapping("/list/popular")
    public ResponseEntity<List<TagResponseDto>> getPopularTags() {
        log.info("[GET][/tag/list/popular] - 인기 태그 조회");
        List<TagResponseDto> tagResponseDtos = tagSearchService.getPopularTags();
        return ResponseEntity.ok().body(tagResponseDtos);
    }

    @Override
    @PostMapping("/list/post")
    public ResponseEntity<List<PostResponseDto>> getPostsByTag(TagRequestDto requestDto) {
        log.info("[POST][/tag/list/post] - 태그 이름으로 게시물 조회: {}", requestDto);
        List<PostResponseDto> postResponseDtos = tagSearchService.getPostsByTag(requestDto.tagName());
        return ResponseEntity.ok().body(postResponseDtos);
    }

}
