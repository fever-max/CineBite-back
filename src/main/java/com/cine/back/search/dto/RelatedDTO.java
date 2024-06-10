package com.cine.back.search.dto;

import lombok.Data;

@Data
public class RelatedDTO {
    
    private int searchRelatedNo; 

    private int searchListNo; 

    private String searchRelatedWord; 

    private int searchRelatedFrequency;

}
