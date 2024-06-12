package com.cine.back.post.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.post.dto.PostRequestDto;
import com.cine.back.post.dto.PostResponseDto;
import com.cine.back.post.entity.PostEntity;
import com.cine.back.post.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final FileService fileService;
    private final PostMapper postMapper;

    @Transactional
    public PostResponseDto writeBoard(PostRequestDto postDto, MultipartFile imgFile) throws IOException {
        try {
            String boardImgUrl = !imgFile.isEmpty() ? fileService.uploadFile(imgFile, "boardImages") : null;
            PostEntity postEntity = postMapper.toPostEntity(postDto, boardImgUrl);
            PostEntity savedPost = postRepository.save(postEntity);
            tagService.saveTags(savedPost, postDto.tagNames());
            log.info("게시글 작성 성공 / No: {}", savedPost.getPostNo());
            PostResponseDto responseDto = postMapper.toResponseDto(savedPost, postDto.tagNames());
            return responseDto;
        } catch (IllegalArgumentException e) {
            log.error("잘못된 요청: {}", e);
            throw e;
        }
    }

    public List<PostResponseDto> getAllBoards() {
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostResponseDto> postResponses = new ArrayList<>();
        for (PostEntity board : postEntities) {
            PostResponseDto responseDto = postMapper.toResponseDto(board,
                    tagService.getTagNamesForBoard(board));
            postResponses.add(responseDto);
        }
        log.info("전체 게시글 조회 성공 / 총 {}개", postResponses.size());
        return postResponses;
    }

    @Transactional
    public PostResponseDto getByBoardNo(Long postNo) {
        try {
            PostEntity postEntity = findPostById(postNo);
            PostEntity updatedBoard = updateHitCount(postEntity);
            PostResponseDto responseDto = postMapper.toResponseDto(updatedBoard,
                    tagService.getTagNamesForBoard(postEntity));
            log.info("게시글 조회 완료 / No: {}", postEntity.getPostNo());
            return responseDto;
        } catch (NoSuchElementException e) {
            log.error("조회할 게시글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteBoard(Long postNo) throws IOException {
        try {
            PostEntity postEntity = findPostById(postNo);
            fileService.deleteFile(postEntity.getImgUrl());
            postRepository.delete(postEntity);
            log.info("게시글 삭제 완료 / No: {}", postNo);
        } catch (NoSuchElementException e) {
            log.error("삭제할 게시글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public PostResponseDto modifyBoard(Long postNo, PostRequestDto postDto, MultipartFile imgFile)
            throws IOException {
        try {
            PostEntity postEntity = findPostById(postNo);
            String boardImgUrl = null;
            if (!imgFile.isEmpty()) {
                fileService.deleteFile(postEntity.getImgUrl());
                boardImgUrl = fileService.uploadFile(imgFile, "boardImages");
            }
            PostEntity updatedBoard = postMapper.updatePostEntity(postEntity, postDto, boardImgUrl);
            List<String> tagNames = tagService.updateTags(updatedBoard, postDto.tagNames());
            log.info("게시글 수정 완료 No: {}", updatedBoard.getPostNo());
            PostResponseDto responseDto = postMapper.toResponseDto(updatedBoard, tagNames);
            return responseDto;
        } catch (NoSuchElementException e) {
            log.error("수정할 게시글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    private PostEntity findPostById(Long postNo) {
        return postRepository.findById(postNo)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글을 찾을 수 없습니다"));
    }

    private PostEntity updateHitCount(PostEntity post) {
        post.setHitCount(post.getHitCount() + 1);
        return post;
    }

}
