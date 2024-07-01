package com.cine.back.board.post.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.post.dto.PostRequestDto;
import com.cine.back.board.post.dto.PostResponseDto;
import com.cine.back.board.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController implements PostControllerDocs {

    private final PostService boardService;

    @Override
    @PostMapping("/post/write")
    public ResponseEntity<Long> saveBoard(PostRequestDto boardDto, MultipartFile imgFile) throws IOException {
        log.info("게시글 저장 컨트롤러, BoardTitle: {}", boardDto.postTitle());
        PostResponseDto responseDto = boardService.writeBoard(boardDto, imgFile);
        return ResponseEntity.ok().body(responseDto.postNo());

    }

    @Override
    @GetMapping("/post/list")
    public ResponseEntity<List<PostResponseDto>> getAllBoards() {
        log.info("전체 게시글 반환 컨트롤러");
        List<PostResponseDto> boards = boardService.getAllBoards();
        return ResponseEntity.ok().body(boards);
    }

    @Override
    @GetMapping("/post/recentList")
    public ResponseEntity<List<PostResponseDto>> getRecentBoards() {
        log.info("최신 게시글 반환 컨트롤러");
        List<PostResponseDto> boards = boardService.getRecentBoards();
        return ResponseEntity.ok().body(boards);
    }

    @Override
    @GetMapping("/post/popularList")
    public ResponseEntity<List<PostResponseDto>> getPopularBoards() {
        log.info("인기 게시글 반환 컨트롤러");
        List<PostResponseDto> boards = boardService.getPopularBoards();
        return ResponseEntity.ok().body(boards);
    }

    @Override
    @GetMapping("/post/{postNo}")
    public ResponseEntity<PostResponseDto> getBoardById(Long postNo) {
        log.info("특정 게시글 반환 컨트롤러, Post No: {}", postNo);
        PostResponseDto board = boardService.getByBoardNo(postNo);
        return ResponseEntity.ok().body(board);
    }

    @Override
    @DeleteMapping("/post/delete/{postNo}")
    public ResponseEntity<String> deleteBoard(Long postNo) throws IOException {
        log.info("특정 게시글 삭제 컨트롤러, Post No: {}", postNo);
        boardService.deleteBoard(postNo);
        return ResponseEntity.ok().body("게시글 삭제 성공");
    }

    @Override
    @PatchMapping("/post/modify/{postNo}")
    public ResponseEntity<Long> updateBoard(Long postNo, PostRequestDto boardDto, MultipartFile imgFile,
            boolean deleteImage)
            throws IOException {
        log.info("특정 게시글 수정 컨트롤러, Board No: {}", postNo);
        PostResponseDto responseDto = boardService.modifyBoard(postNo, boardDto, imgFile, deleteImage);
        return ResponseEntity.ok().body(responseDto.postNo());
    }

}
