package com.cine.back.movieList.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Embeddable // 다른 클래스에 포함
public class Credits {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @OneToMany(cascade = CascadeType.ALL)
    // private List<Cast> cast;
    
    // @Transient
    @JsonProperty("cast")
    @ElementCollection
    private List<Cast> cast;

    // 필드가 하나이기 때문에 클래스 자체에 @Embeddable을 사용하는 것.
    // 필드가 복수 개라면 적용할 필드 값에 @Embedded를 사용하면 된다.
}
