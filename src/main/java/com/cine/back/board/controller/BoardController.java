package com.cine.back.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.dto.BoardRequestDto;
import com.cine.back.board.dto.BoardResponseDto;
import com.cine.back.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController implements BoardControllerDocs {

    private final BoardService boardService;

    @Override
    @PostMapping("/write")
    public ResponseEntity<Long> saveBoard(BoardRequestDto boardDto, MultipartFile imgFile) {
        log.info("게시글 저장 컨트롤러, BoardTitle: {}", boardDto.getBoardTitle());
        try {
            BoardResponseDto responseDto = boardService.writeBoard(boardDto, imgFile);
            return ResponseEntity.ok().body(responseDto.getBoardNo());
        } catch (IOException e) {
            log.error("게시글 저장 중 오류 발생: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.error("잘못된 요청: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        try {
            log.info("전체 게시글 반환 컨트롤러");
            List<BoardResponseDto> boards = boardService.getAllBoards();
            return ResponseEntity.ok().body(boards);
        } catch (Exception e) {
            log.error("게시글 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping("/post/{no}")
    public ResponseEntity<BoardResponseDto> getBoardById(Long boardNo) {
        log.info("특정 게시글 반환 컨트롤러, Board No: {}", boardNo);
        try {
            BoardResponseDto board = boardService.getByBoardNo(boardNo);
            return ResponseEntity.ok().body(board);
        } catch (NoSuchElementException e) {
            log.error("조회할 게시글이 없음: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("게시글 조회 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @DeleteMapping("/delete/{no}")
    public ResponseEntity<String> deleteBoard(Long boardNo) {
        log.info("특정 게시글 삭제 컨트롤러, Board No: {}", boardNo);
        try {
            boardService.deleteBoard(boardNo);
            return ResponseEntity.ok().body("게시글 삭제 성공");
        } catch (NoSuchElementException e) {
            log.error("삭제할 게시글이 없음: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            log.error("게시글 삭제 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @PutMapping("/modify/{no}")
    public ResponseEntity<Long> updateBoard(Long boardNo, BoardRequestDto boardDto, MultipartFile imgFile) {
        log.info("특정 게시글 수정 컨트롤러, Board No: {}", boardNo);
        try {
            BoardResponseDto responseDto = boardService.modifyBoard(boardNo, boardDto, imgFile);
            return ResponseEntity.ok().body(responseDto.getBoardNo());
        } catch (NoSuchElementException e) {
            log.error("수정할 게시글이 없음: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            log.error("게시글 수정 중 오류 발생: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
