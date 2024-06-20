package com.cine.back.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cine.back.movieList.exception.AlreadyEvaluatedException;
import com.cine.back.movieList.exception.MovieNotFoundException;
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * ExceptionHandler
     * required : HttpStatus, Exception e
     * @ExceptionHandler()
     */

    

    private static final int NOT_FOUND_ERROR = 404;
    private static final int CONFILCT_ERROR = 409;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 " + e.getMessage());
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFoundException(MovieNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyEvaluatedException.class)
    public ResponseEntity<?> handleAlreadyEvaluatedException(AlreadyEvaluatedException e) {
        return ResponseEntity.status(CONFILCT_ERROR).body(e.getMessage());
    }



}
