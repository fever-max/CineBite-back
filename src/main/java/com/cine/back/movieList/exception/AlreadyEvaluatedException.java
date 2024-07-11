package com.cine.back.movieList.exception;

import com.cine.back.advice.BadRequestException;

public class AlreadyEvaluatedException extends BadRequestException {
    
    private static final String message = "이미 평가된 영화입니다.";

    public AlreadyEvaluatedException() {
        super(message);
    }
}
