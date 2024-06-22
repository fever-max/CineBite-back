package com.cine.back.movieList.dto;


import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Credits {
  @JsonProperty("cast")
  @ElementCollection
  private List<Cast> cast;
}