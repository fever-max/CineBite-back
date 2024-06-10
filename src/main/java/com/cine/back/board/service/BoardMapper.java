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
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .userEmail(boardDto.getUserEmail())
                .boardCreatedDate(LocalDateTime.now())
                .boardImgUrl(boardImgUrl)
                .build();
    }

    public BoardResponseDto toBoardResponseDto(BoardEntity board, List<String> tagNames) {
        return BoardResponseDto.builder()
                .boardNo(board.getBoardNo())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .userEmail(board.getUserEmail())
                .tagNames(tagNames)
                .boardHitCount(board.getBoardHitCount())
                .boardCommentCount(board.getBoardCommentCount())
                .boardLikeCount(board.getBoardLikeCount())
                .boardImgUrl(board.getBoardImgUrl())
                .boardCreatedDate(board.getBoardCreatedDate())
                .build();
    }

    public BoardEntity updateBoardEntity(BoardEntity boardEntity, BoardRequestDto boardDto, String boardImgUrl) {
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardUpdateDate(LocalDateTime.now());
        boardEntity.setBoardImgUrl(boardImgUrl);
        return boardEntity;
    }
}
