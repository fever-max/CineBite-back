package com.cine.back.search.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "search_list")
@Getter
@Setter
public class SearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_list_no")
    private int searchListNo; // 최근 검색 리스트 ID

    @Column(name = "user_email", length = 100)
    private String userEmail; // 사용자 이메일

    // FetchType : oneToMany시 명시적으로 지정
    // 기본값(지연 로딩) : FetchType.LAZY
    // 즉시 로딩: FetchType.EAGER
    @OneToMany(mappedBy = "searchEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SearchKeywordEntity> searchKeywords; // 최근 검색 리스트

    @Column(name = "search_list_time")
    private LocalDateTime searchListTime = LocalDateTime.now(); // 검색한 시간

}
