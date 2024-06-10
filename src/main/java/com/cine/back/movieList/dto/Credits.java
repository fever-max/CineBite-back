package com.cine.back.movieList.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Embeddable
public class Credits {
    
    @JsonProperty("cast")
    @Transient
    private List<Cast> cast;
}
