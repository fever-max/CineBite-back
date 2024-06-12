package com.cine.back.movieList.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Cast {
    private String name;
    private String profile_path;
}