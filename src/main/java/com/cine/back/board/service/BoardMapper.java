package com.cine.back.board.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cine.back.board.dto.BoardRequestDto;
import com.cine.back.board.dto.BoardResponseDto;
import com.cine.back.board.entity.BoardEntity;

@Component
public class BoardMapper {

    public BoardEntity toBoardEntity(BoardRequestDto boardDto, String boardImgUrl) {
        return BoardEntity.builder()
                .boardTitle(boardDto.boardTitle())
                .boardContent(boardDto.boardContent())
                .userId(boardDto.userId())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgUrl(boardImgUrl)
                .build();
    }

    public BoardResponseDto toBoardResponseDto(BoardEntity board, List<String> tagNames) {
        return BoardResponseDto.of(
                board.getBoardNo(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getUserId(),
                tagNames,
                board.getBoardHitCount(),
                board.getBoardCommentCount(),
                board.getBoardLikeCount(),
                board.getBoardImgUrl(),
                board.getBoardCreatedDate());
    }

    public BoardEntity updateBoardEntity(BoardEntity boardEntity, BoardRequestDto boardDto, String boardImgUrl) {
        boardEntity.setBoardTitle(boardDto.boardTitle());
        boardEntity.setBoardContent(boardDto.boardContent());
        boardEntity.setBoardUpdateDate(LocalDateTime.now());
        boardEntity.setBoardImgUrl(boardImgUrl);
        return boardEntity;
    }
}
