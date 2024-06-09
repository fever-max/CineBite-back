package com.cine.back.board.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.board.dto.BoardRequestDto;
import com.cine.back.board.dto.BoardResponseDto;
import com.cine.back.board.entity.BoardEntity;
import com.cine.back.board.entity.BoardTagEntity;
import com.cine.back.board.entity.BoardTagMapEntity;
import com.cine.back.board.repository.BoardRepository;
import com.cine.back.board.repository.BoardTagMapRepository;
import com.cine.back.board.repository.BoardTagRepository;
import com.cine.back.config.file.S3Uploader;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final S3Uploader s3Uploader;

    private final BoardRepository boardRepository;
    private final BoardTagMapRepository boardTagMapRepository;
    private final BoardTagRepository boardTagRepository;

    public BoardEntity writeBoard(BoardRequestDto boardDto, MultipartFile imgFile) throws IOException {
        // 사진 url 저장
        String boardImgUrl = !imgFile.isEmpty() ? s3Uploader.upload(imgFile, "boardImages") : null;
        BoardEntity boardEntity = buildBoardEntity(boardDto, boardImgUrl);
        BoardEntity savedBoard = boardRepository.save(boardEntity);
        // 태그 저장
        saveTags(savedBoard, boardDto.getTagNames());
        log.info("게시글 작성 성공 / No: {}", savedBoard.getBoardNo());
        return savedBoard;
    }

    // 게시글 전체 반환
    public List<BoardResponseDto> getAllBoards() {
        // 모든 게시글 조회
        List<BoardEntity> boardEntities = boardRepository.findAll();
        // 각 게시물 반복하면서 태그 받아옴
        List<BoardResponseDto> boardResponses = new ArrayList<>();
        for (BoardEntity board : boardEntities) {
            // 1개씩 받아와서 리스트에 저장
            BoardResponseDto responseDto = buildBoardWithTags(board);
            boardResponses.add(responseDto);
        }
        log.info("전체 게시글 조회 성공 / 총 {}개", boardResponses.size());
        return boardResponses;
    }

    // 게시글 no 조회
    public BoardResponseDto getByBoardNo(Long boardNo) {
        BoardEntity board = hasBoard(boardNo);
        BoardResponseDto responseDto = buildBoardResponseDto(board);
        log.info("게시글 조회 완료 / No: {}", board.getBoardNo());
        return responseDto;
    }

    // 게시글 no 삭제
    @Transactional
    public void deleteBoard(Long boardNo) throws IOException {
        BoardEntity boardEntity = hasBoard(boardNo);

        // s3 파일 삭제
        s3Uploader.deleteFile(boardEntity.getBoardImgUrl());
        // 게시물 삭제
        boardRepository.delete(boardEntity);
        log.info("게시글 삭제 완료 / No: {}", boardNo);
    }

    // 게시글 수정
    public BoardEntity modifyBoard(Long boardNo, BoardRequestDto boardDto, MultipartFile imgFile) throws IOException {
        BoardEntity boardEntity = hasBoard(boardNo);
        // 사진이 있을 경우 (기존 사진 삭제 후 재업로드)
        String boardImgUrl = null;
        if (!imgFile.isEmpty()) {
            s3Uploader.deleteFile(boardEntity.getBoardImgUrl()); // s3 파일 삭제
            boardImgUrl = s3Uploader.upload(imgFile, "boardImages"); // s3 파일 저장
        }
        // 업데이트 후 재저장
        BoardEntity updatedBoard = updateBoardEntity(boardEntity, boardDto, boardImgUrl);
        // 태그 수정
        updateTags(updatedBoard, boardDto.getTagNames());
        log.info("게시글 수정 완료 No: {}", updatedBoard.getBoardNo());
        return updatedBoard;
    }

    // 게시글 생성 빌더
    private BoardEntity buildBoardEntity(BoardRequestDto boardDto, String boardImgUrl) {
        return BoardEntity.builder()
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .userEmail(boardDto.getUserEmail())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgUrl(boardImgUrl)
                .build();
    }

    // 업데이트
    private BoardEntity updateBoardEntity(BoardEntity boardEntity, BoardRequestDto boardDto, String boardImgUrl) {
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardUpdateDate(LocalDateTime.now());
        boardEntity.setBoardImgUrl(boardImgUrl);
        return boardRepository.save(boardEntity);
    }

    // 게시글 확인
    private BoardEntity hasBoard(Long boardNo) {
        BoardEntity board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다"));
        log.info("게시글 조회 완료 / No: {}", board.getBoardNo());
        return board;
    }

    // 게시글 반환 빌더
    private BoardResponseDto buildBoardResponseDto(BoardEntity board) {
        BoardResponseDto responseDto = BoardResponseDto.builder()
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .userEmail(board.getUserEmail())
                .tagNames(getTagNamesForBoard(board))
                .boardHitCount(board.getBoardHitCount())
                .boardCommentCount(board.getBoardCommentCount())
                .boardLikeCount(board.getBoardLikeCount())
                .boardImgUrl(board.getBoardImgUrl())
                .boardCreatedDate(board.getBoardCreatedDate())
                .build();
        return responseDto;
    }

    // 태그 저장
    private void saveTags(BoardEntity savedBoard, List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                BoardTagEntity tag = new BoardTagEntity();
                tag.setName(tagName);
                boardTagRepository.save(tag);
                // 맵핑 아이디 저장
                BoardTagMapEntity mapEntity = new BoardTagMapEntity();
                mapEntity.setBoard(savedBoard);
                mapEntity.setTag(tag);
                boardTagMapRepository.save(mapEntity);
            }
        }
    }

    // 태그 수정 및 저장
    private void updateTags(BoardEntity boardEntity, List<String> newTagNames) {
        // 기존 맵핑 & 태그 엔티티 삭제
        List<BoardTagMapEntity> tagMappings = boardEntity.getBoardTagMappings();
        boardTagMapRepository.deleteAll(tagMappings);
        // 새로운 태그 저장
        saveTags(boardEntity, newTagNames);
    }

    // 태그 조회
    private List<String> getTagNamesForBoard(BoardEntity board) {
        List<String> tagNames = new ArrayList<>();
        // 게시글에 연동된 맵핑 엔티티 조회
        List<BoardTagMapEntity> tagMappings = board.getBoardTagMappings();
        // 각 태그 매핑 엔티티에서 태그 엔티티의 이름 조회, 배열로 추가
        for (BoardTagMapEntity mapping : tagMappings) {
            BoardTagEntity tag = mapping.getTag();
            tagNames.add(tag.getName());
        }
        return tagNames;
    }

    // 게시글마다 태그 붙여서 반환
    private BoardResponseDto buildBoardWithTags(BoardEntity board) {
        BoardResponseDto responseDto = buildBoardResponseDto(board);
        responseDto.setTagNames(getTagNamesForBoard(board));
        return responseDto;
    }

}
