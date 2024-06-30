package com.cine.back.movieList.exception;

import com.cine.back.advice.BadRequestException;

public class MovieNotFoundException extends BadRequestException {
    
    private static final String message = "해당 영화를 찾을 수 없습니다.";

    public MovieNotFoundException() {
        super(message);
    }
}
