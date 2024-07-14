package com.cine.back.board.search.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.post.entity.PostEntity;
import com.cine.back.board.post.repository.PostRepository;
import com.cine.back.board.post.service.PostMapper;
import com.cine.back.board.post.service.TagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final PostMapper postMapper;

    public List<PostResponseDto> getPostsByTitle(
            String postContent) {
        List<PostEntity> postEntities = postRepository.findByPostTitleContaining(postContent);
        List<PostResponseDto> postResponseDtos = postMapper.toResponseDtos(postEntities, tagService);
        log.info("# 게시글 제목 조회 성공 - 총 {}개", postResponseDtos.size());
        return postResponseDtos;
    }

    public List<PostResponseDto> getPostsByContent(String postContent) {
        List<PostEntity> postEntities = postRepository.findByPostContentContaining(postContent);
        List<PostResponseDto> postResponseDtos = postMapper.toResponseDtos(postEntities, tagService);
        log.info("# 게시글 내용 조회 성공 - 총 {}개", postResponseDtos.size());
        return postResponseDtos;

    }

    public List<PostResponseDto> getPostsByUser(String userId) {
        List<PostEntity> postEntities = postRepository.findByUserIdContaining(userId);
        List<PostResponseDto> postResponseDtos = postMapper.toResponseDtos(postEntities, tagService);
        log.info("# 게시글 작성자 조회 성공 - 총 {}개", postResponseDtos.size());
        return postResponseDtos;
    }

}
