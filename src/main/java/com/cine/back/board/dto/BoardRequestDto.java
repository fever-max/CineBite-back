package com.cine.back.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardRequestDto {

    private String boardTitle;

    private String boardContent;

    private String userEmail;

    private List<String> tagNames;

}
