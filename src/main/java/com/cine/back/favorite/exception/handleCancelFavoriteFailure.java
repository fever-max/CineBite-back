package com.cine.back.favorite.exception;

import com.cine.back.advice.BadRequestException;

public class handleCancelFavoriteFailure extends BadRequestException {
    
    public static final String message = "찜 취소 작업 실패";

    public handleCancelFavoriteFailure() {
        super(message);
    }
}