package com.cine.back.movieList.request;

import java.time.LocalDateTime;

public record UserRevalueRequest( 
    int movieId,
    String userId,
    LocalDateTime deletedDate,
    boolean checkDeleted
    ) {}
