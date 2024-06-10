package com.cine.back.board.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.dto.BoardRequestDto;
import com.cine.back.board.dto.BoardResponseDto;
import com.cine.back.board.entity.BoardEntity;
import com.cine.back.board.repository.BoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardTagService boardTagService;
    private final FileService fileService;
    private final BoardMapper boardMapper;

    @Transactional
    public BoardResponseDto writeBoard(BoardRequestDto boardDto, MultipartFile imgFile) throws IOException {
        try {
            String boardImgUrl = !imgFile.isEmpty() ? fileService.uploadFile(imgFile, "boardImages") : null;
            BoardEntity boardEntity = boardMapper.toBoardEntity(boardDto, boardImgUrl);
            BoardEntity savedBoard = boardRepository.save(boardEntity);
            boardTagService.saveTags(savedBoard, boardDto.getTagNames());
            log.info("게시글 작성 성공 / No: {}", savedBoard.getBoardNo());
            BoardResponseDto responseDto = boardMapper.toBoardResponseDto(savedBoard, boardDto.getTagNames());
            return responseDto;
        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public List<BoardResponseDto> getAllBoards() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardResponseDto> boardResponses = new ArrayList<>();
        for (BoardEntity board : boardEntities) {
            BoardResponseDto responseDto = boardMapper.toBoardResponseDto(board,
                    boardTagService.getTagNamesForBoard(board));
            boardResponses.add(responseDto);
        }
        log.info("전체 게시글 조회 성공 / 총 {}개", boardResponses.size());
        return boardResponses;
    }

    public BoardResponseDto getByBoardNo(Long boardNo) {
        try {
            BoardEntity board = findBoardById(boardNo);
            BoardResponseDto responseDto = boardMapper.toBoardResponseDto(board,
                    boardTagService.getTagNamesForBoard(board));
            log.info("게시글 조회 완료 / No: {}", board.getBoardNo());
            return responseDto;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    @Transactional
    public void deleteBoard(Long boardNo) throws IOException {
        try {
            BoardEntity boardEntity = findBoardById(boardNo);
            fileService.deleteFile(boardEntity.getBoardImgUrl());
            boardRepository.delete(boardEntity);
            log.info("게시글 삭제 완료 / No: {}", boardNo);
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    @Transactional
    public BoardResponseDto modifyBoard(Long boardNo, BoardRequestDto boardDto, MultipartFile imgFile)
            throws IOException {
        try {
            BoardEntity boardEntity = findBoardById(boardNo);
            String boardImgUrl = null;
            if (!imgFile.isEmpty()) {
                fileService.deleteFile(boardEntity.getBoardImgUrl());
                boardImgUrl = fileService.uploadFile(imgFile, "boardImages");
            }
            BoardEntity updatedBoard = boardMapper.updateBoardEntity(boardEntity, boardDto, boardImgUrl);
            List<String> tagNames = boardTagService.updateTags(updatedBoard, boardDto.getTagNames());
            log.info("게시글 수정 완료 No: {}", updatedBoard.getBoardNo());
            BoardResponseDto responseDto = boardMapper.toBoardResponseDto(updatedBoard, tagNames);
            return responseDto;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (IOException e) {

            throw e;
        }
    }

    private BoardEntity findBoardById(Long boardNo) {
        return boardRepository.findById(boardNo)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글을 찾을 수 없습니다"));
    }

}
