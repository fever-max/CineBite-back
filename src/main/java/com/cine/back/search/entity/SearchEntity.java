package com.cine.back.search.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "search_list")
@Getter
@Setter
public class SearchEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_list_no")
    private int searchListNo; //최근 검색 리스트 ID

    @Column(name = "user_id", length = 50)
    private String userId; //사용자 아이디

    @OneToMany(mappedBy = "searchEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SearchKeywordEntity> searchKeywords = new ArrayList<>();


    @Column(name = "search_list_time")
    private LocalDateTime searchListTime = LocalDateTime.now(); // 검색한 시간

    // 검색 키워드를 추가하는 메서드
    public void addSearchKeyword(SearchKeywordEntity keywordEntity) {
        searchKeywords.add(keywordEntity);
        keywordEntity.setSearchEntity(this);
    }
}
