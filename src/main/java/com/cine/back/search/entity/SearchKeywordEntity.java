package com.cine.back.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "search_keyword")
@Getter
@Setter
public class SearchKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_keyword_no")
    private int searchKeywordNo; //검색어 ID

    @Column(name = "keyword")
    private String keyword; //검색어

    @ManyToOne
    @JoinColumn(name = "search_list_no")
    private SearchEntity searchEntity; //검색어 번호

}
