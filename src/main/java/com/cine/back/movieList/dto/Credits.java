package com.cine.back.movieList.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Embeddable // 다른 클래스에 포함
public class Credits {
    
    @JsonProperty("cast")
    @Transient
    private List<Cast> cast;

    // 필드가 하나이기 때문에 클래스 자체에 @Embeddable을 사용하는 것.
    // 필드가 복수 개라면 적용할 필드 값에 @Embedded를 사용하면 된다.
}
