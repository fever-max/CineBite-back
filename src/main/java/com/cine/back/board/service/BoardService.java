package com.cine.back.board.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.dto.BoardDto;
import com.cine.back.board.entity.BoardEntity;
import com.cine.back.board.repository.BoardRepository;
import com.cine.back.config.file.S3Uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;

    public BoardEntity writeBoard(BoardDto boardDto, MultipartFile imgFile) throws IOException {
        // 사진이 있을 경우
        String boardImgUrl = null;
        if (!imgFile.isEmpty()) {
            boardImgUrl = s3Uploader.upload(imgFile, "boardImages");
        }
        BoardEntity boardEntity = buildBoardEntity(boardDto, boardImgUrl);
        BoardEntity savedBoard = boardRepository.save(boardEntity);
        log.info("게시글 작성 성공 / No: {}", savedBoard.getBoardNo());
        return savedBoard;
    }

    // 게시글 전체 반환
    public List<BoardEntity> getAllBoards() {
        List<BoardEntity> boards = boardRepository.findAll();
        log.info("전체 게시글 조회 성공 / 총 {}개", boards.size());
        return boards;
    }

    // 게시글 no 조회
    public BoardEntity getBoardByNo(Long boardNo) {
        BoardEntity board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다"));
        log.info("게시글 조회 완료 / No: {}", board.getBoardNo());
        return board;
    }

    // 게시글 no 삭제
    public void deleteBoard(Long boardNo) throws IOException {
        BoardEntity boardEntity = getBoardByNo(boardNo);
        boardRepository.delete(boardEntity);
        s3Uploader.deleteFile(boardEntity.getBoardImgUrl()); // s3 파일 삭제
        log.info("게시글 삭제 완료 / No: {}", boardNo);

    }

    // 게시글 수정
    public BoardEntity modifyBoard(Long boardNo, BoardDto boardDto, MultipartFile imgFile) throws IOException {
        BoardEntity boardEntity = getBoardByNo(boardNo);
        // 사진이 있을 경우 (기존 사진 삭제 후 재업로드)
        String boardImgUrl = null;
        if (!imgFile.isEmpty()) {
            s3Uploader.deleteFile(boardEntity.getBoardImgUrl()); // s3 파일 삭제
            boardImgUrl = s3Uploader.upload(imgFile, "boardImages"); // s3 파일 저장
        }
        updateBoardEntity(boardEntity, boardDto, boardImgUrl);
        BoardEntity updatedBoard = boardRepository.save(boardEntity);
        log.info("게시글 수정 완료 No: {}", updatedBoard.getBoardNo());
        return updatedBoard;
    }

    private BoardEntity buildBoardEntity(BoardDto boardDto, String boardImgUrl) {
        return BoardEntity.builder()
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .userEmail(boardDto.getUserEmail())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgUrl(boardImgUrl)
                .boardListTag(boardDto.getBoardListTag())
                .build();
    }

    private void updateBoardEntity(BoardEntity boardEntity, BoardDto boardDto, String boardImgUrl) {
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardUpdateDate(LocalDateTime.now());
        boardEntity.setBoardImgUrl(boardImgUrl);
        boardEntity.setBoardListTag(boardDto.getBoardListTag());
    }

}
