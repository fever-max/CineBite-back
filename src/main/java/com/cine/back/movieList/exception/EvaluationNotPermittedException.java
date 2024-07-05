package com.cine.back.movieList.exception;

import com.cine.back.advice.BadRequestException;

public class EvaluationNotPermittedException extends BadRequestException {
    
    private static final String message = "평가를 취소한 영화라 잠시 후에 이용해주세요.";

    public EvaluationNotPermittedException(long secondsRemaining) {
        super(message + "남은 시간: " + secondsRemaining + "초");
    }
}
