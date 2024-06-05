package com.cine.back.search.searchList;

import java.time.LocalDateTime;
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
public class SearchEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_list_no")
    private int searchListNo; //검색어 ID

    @NotNull
    @Column(name = "user_email", length = 100)
    private String userEmail; //사용자 이메일

    @NotNull
    @Column(name = "search_list_word", length = 1500)
    private String searchListWord; //검색어

    @Column(name = "search_list_time")
    private LocalDateTime searchListTime = LocalDateTime.now(); //검색한 시간
}
