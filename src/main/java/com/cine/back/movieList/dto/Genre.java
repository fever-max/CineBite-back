package com.cine.back.movieList.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Genre {

    private int id;
    private String name;
}
