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
        String boardImgName = null;
        String boardImgUrl = null;
        if (!imgFile.isEmpty()) {
            boardImgUrl = s3Uploader.upload(imgFile, "boardImages");
        }
        BoardEntity boardEntity = BoardEntity.builder()
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .userEmail(boardDto.getUserEmail())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgName(boardImgName)
                .boardImgUrl(boardImgUrl)
                .boardListTag(boardDto.getBoardListTag())
                .build();

        return boardRepository.save(boardEntity);
    }

    // 게시글 전체 반환
    public List<BoardEntity> getAllBoards() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        return boardEntities;
    }

    // 게시글 no 조회
    public BoardEntity getBoardByNo(Long boardNo) {
        try {
            return boardRepository.findByBoardNo(boardNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다"));
        } catch (IllegalArgumentException e) {
            log.error("게시글 조회 중 오류 발생: {} ", e);
            throw e;
        }
    }

    // 게시글 no 삭제
    public void deleteBoard(Long boardNo) {
        BoardEntity boardEntity = boardRepository.findByBoardNo(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다"));
        boardRepository.delete(boardEntity);
    }

    // 게시글 수정
    public BoardEntity modifyBoard(BoardDto boardDto, MultipartFile imgFile) throws IOException {
        // 사진이 있을 경우
        String boardImgName = null;
        String boardImgUrl = null;
        if (!imgFile.isEmpty()) {
            boardImgUrl = s3Uploader.upload(imgFile, "boardImages");
        }
        BoardEntity boardEntity = BoardEntity.builder()
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgName(boardImgName)
                .boardImgUrl(boardImgUrl)
                .boardListTag(boardDto.getBoardListTag())
                .build();

        return boardRepository.save(boardEntity);
    }

}
