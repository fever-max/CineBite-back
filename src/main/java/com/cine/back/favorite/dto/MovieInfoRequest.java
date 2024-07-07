package com.cine.back.favorite.dto;

public record MovieInfoRequest(
                int movieId,
                String posterPath,
                String title,
                double tomatoScore) { }
