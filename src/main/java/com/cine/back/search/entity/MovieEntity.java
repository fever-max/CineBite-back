package com.cine.back.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "movie_list")
@Getter
@Setter
public class MovieEntity {
 
    @Id
    @Column(name = "movie_cd")
    private String movieCd; // 영화코드

    @Column(name = "movie_nm", length = 500)
    private String movieNm; // 영화명

    @Column(name = "open_dt")
    private String openDt; // 개봉날짜

    @Column(length = 100)
    private String genres; // 장르

    @Column(length = 100)
    private String actors; // 배우

    @Column(name = "poster_url")
    private String posterUrl; // 포스터
}

