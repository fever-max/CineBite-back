package com.cine.back.movieList.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import lombok.Getter;
@Getter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Cast {
  private String name;
  private String profile_path;
}
