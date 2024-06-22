package com.cine.back.search.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SearchDTO {
    
    private int searchListNo;

    private String userEmail;

    private List<SearchKeywordDTO> searchKeywords;
    
    private LocalDateTime searchListTime;

}
