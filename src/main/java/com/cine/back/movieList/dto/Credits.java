package com.cine.back.movieList.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable // 다른 클래스에 포함
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Credits {

    // 값타입 컬렉션으로 'Credits에 종속되어 movieDetailEntity와 다대일 관계를 갖는다.'
    @JsonProperty("cast")
    @ElementCollection 
    private List<Cast> cast;  
}
