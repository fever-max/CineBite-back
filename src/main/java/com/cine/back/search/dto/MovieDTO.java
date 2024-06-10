package com.cine.back.search.dto;

import lombok.Data;

@Data
public class MovieDTO {

    private String movieCd; 

    private String movieNm; 

    private String openDt;

    private String genres; 

    private String actors; 
    
    private String posterUrl; 
}
