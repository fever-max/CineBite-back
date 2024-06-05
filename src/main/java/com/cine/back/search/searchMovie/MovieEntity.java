package com.cine.back.search.searchMovie;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MovieEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_related_no")
    private int searchRelatedNo; //연관검색어 ID

    @Column(name = "search_list_no")
    private int searchListNo; //검색어 ID

    @NotNull
    @Column(name = "search_related_word", length = 1500)
    private String searchRelatedWord; //연관검색어

    @Column(name = "search_related_frequency")
    private int searchRelatedFrequency; //연관검색어 빈도수
}
