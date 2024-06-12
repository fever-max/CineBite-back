package com.cine.back.movieList.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable // 다른 클래스에 포함
public class Credits {

    @JsonProperty("cast")
    @ElementCollection // 값타입 컬렉션으로 'Credits에 종속되어 movieDetailEntity와 다대일 관계를 갖는다.'
    private List<Cast> cast;    // 외래키

    /* JPA가 값타입 컬렉션에 객체가 추가되고 삭제되는 것은 엔티티 안에서 일어나는 현상이기 때문에 감지할 수 있지만
    컬렉션 안의 컬럼의 데이터(필드)가 변경되는 것은 감지하지 못한다.
    그래서 절대 객체의 필드가 수정되면 안되기 때문에 setter는 금지다.
    또, 수정하려면 아예 기존 객체를 삭제하고 새로운 객체를 컬렉션에 추가해야 한다. 
    때문에 필드의 수가 적고 불변 객체가 쓰일 때 사용하기 좋다.*/
    
    // * 장르, 배우들은 상세 정보 영화번호에 종속되어야 하기때문에 사용했다.

    // 필드가 하나이기 때문에 클래스 자체에 @Embeddable을 사용하는 것.
    // 필드가 복수 개라면 적용할 필드 값에 @Embedded를 사용하면 된다.
}
