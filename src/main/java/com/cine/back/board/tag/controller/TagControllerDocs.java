package com.cine.back.board.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.tag.dto.TagResponseDto;

import java.util.*;

public interface TagControllerDocs {

    public ResponseEntity<List<TagResponseDto>> getRecentTags();

    public ResponseEntity<List<TagResponseDto>> getPopularTags();

    public ResponseEntity<List<PostResponseDto>> getPostsByTag(@PathVariable(value = "tagName") String tagName);

}
