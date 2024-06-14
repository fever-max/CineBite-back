package com.cine.back.movieList.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;
@Data
@Embeddable
public class Genre {

    private int id;
    private String name;
}
