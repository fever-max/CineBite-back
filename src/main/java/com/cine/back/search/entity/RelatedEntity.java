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

@Entity(name = "search_related")
@Getter
@Setter
public class RelatedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_related_no")
    private int searchRelatedNo; // 연관 검색어 ID

    @ManyToOne
    @JoinColumn(name = "search_keyword_no", nullable = false)
    private SearchKeywordEntity searchKeywordEntity; // 검색어 번호, fk

    @Column(name = "search_related_word", length = 1500, nullable = false)
    private String searchRelatedWord; // 연관 검색어

    @Column(name = "search_related_count")
    private int searchRelatedCount; // 연관 검색어 빈도수
}

