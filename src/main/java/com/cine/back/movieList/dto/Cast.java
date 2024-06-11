package com.cine.back.movieList.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Cast {
    private String name;
    private String profile_path;
}