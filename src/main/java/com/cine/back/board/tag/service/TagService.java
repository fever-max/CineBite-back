package com.cine.back.board.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cine.back.board.post.repository.TagMapRepository;
import com.cine.back.board.post.repository.TagRepository;
import com.cine.back.board.tag.dto.TagResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapRepository tagMapRepository;
    private final TagRepository tagRepository;

    public List<TagResponseDto> getRecentTags() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecentTags'");
    }

}
