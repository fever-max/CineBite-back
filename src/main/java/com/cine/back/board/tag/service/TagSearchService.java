package com.cine.back.board.tag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.post.entity.PostEntity;
import com.cine.back.board.post.entity.PostTagEntity;
import com.cine.back.board.post.repository.TagRepository;
import com.cine.back.board.post.service.PostMapper;
import com.cine.back.board.post.service.TagService;
import com.cine.back.board.tag.dto.TagResponseDto;
import com.cine.back.board.util.EntityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagSearchService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;
    private final PostMapper postMapper;

    private final EntityUtil entityUtil;
    private final TagService tagService;

    public List<TagResponseDto> getRecentTags() {
        List<PostTagEntity> recentTags = tagRepository.findTop5ByOrderByCreatedDateDesc();
        List<TagResponseDto> tagResponseDtos = tagMapper.toResponseDtos(recentTags);
        return tagResponseDtos;
    }

    public List<TagResponseDto> getPopularTags() {
        List<PostTagEntity> popularTags = tagRepository.findTopUsedTags(PageRequest.of(0, 5));
        List<TagResponseDto> tagResponseDtos = tagMapper.toResponseDtos(popularTags);
        return tagResponseDtos;
    }

    public List<PostResponseDto> getPostsByTag(String tagName) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        try {
            List<Long> postNos = tagRepository.findPostNumbersByTagName(tagName);
            for (Long postNo : postNos) {
                PostEntity postEntity = entityUtil.findPostById(postNo);
                PostResponseDto responseDto = postMapper.toResponseDto(postEntity,
                        tagService.getTagNamesForBoard(postEntity));
                postResponseDtos.add(responseDto);
            }
        } catch (NoSuchElementException e) {
            log.error("# {} 태그에 해당하는 게시물을 찾을 수 없음 - {} ", tagName, e.getMessage());
            throw e;
        }
        return postResponseDtos;
    }

}
