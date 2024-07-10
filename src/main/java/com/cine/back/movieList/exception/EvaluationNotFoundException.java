package com.cine.back.movieList.exception;

import com.cine.back.advice.BadRequestException;

public class EvaluationNotFoundException extends BadRequestException{
    
    private static final String message = "평가를 찾을 수 없습니다.";

    public EvaluationNotFoundException() {
        super(message);
    }
}
