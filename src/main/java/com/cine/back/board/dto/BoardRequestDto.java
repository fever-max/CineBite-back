package com.cine.back.board.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoardRequestDto {

    private String boardTitle;

    private String boardContent;

    private String userEmail;

    private List<String> tagNames;

}
