package com.cine.back.movieList.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record UserRevalueRequest( 
    @NotBlank(message = "[NotBlank] 영화 ID ")int movieId,
    @NotBlank(message = "[NotBlank] 사용자 ID ")String userId,
    LocalDateTime deletedDate,
    @NotBlank(message = "[NotBlank] 사용자 평가 삭제 유무 ")boolean checkDeleted
    ) {}
