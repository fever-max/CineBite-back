package com.cine.back.movieList.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Genre {

    private int id;
    private String name;
}
