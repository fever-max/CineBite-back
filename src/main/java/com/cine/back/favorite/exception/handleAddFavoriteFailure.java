package com.cine.back.favorite.exception;

import com.cine.back.advice.BadRequestException;

public class handleAddFavoriteFailure extends BadRequestException {
    
    public static final String message = "찜 추가 작업 실패";

    public handleAddFavoriteFailure() {
        super(message);
    }
}
