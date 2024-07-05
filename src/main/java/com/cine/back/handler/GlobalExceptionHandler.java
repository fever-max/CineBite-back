package com.cine.back.handler;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import com.cine.back.favorite.exception.handleAddFavoriteFailure;
import com.cine.back.favorite.exception.handleCancelFavoriteFailure;
import com.cine.back.movieList.exception.AlreadyEvaluatedException;
import com.cine.back.movieList.exception.EvaluationNotPermittedException;
import com.cine.back.movieList.exception.MovieNotFoundException;
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * ExceptionHandler
     * required : HttpStatus, Exception e
     * @ExceptionHandler()
     */

    
    private static final int BAD_REQUEST_ERROR = 400;
    private static final int NOT_FOUND_ERROR = 404;
    private static final int CONFILCT_ERROR = 409;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 중 오류: " + e.getMessage());
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청:  " + e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("데이터를 찾을 수 없음: " + e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("엔티티를 찾을 수 없음: " + e.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFoundException(MovieNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyEvaluatedException.class)
    public ResponseEntity<?> handleAlreadyEvaluatedException(AlreadyEvaluatedException e) {
        return ResponseEntity.status(CONFILCT_ERROR).body(e.getMessage());
    }
    
    @ExceptionHandler(handleCancelFavoriteFailure.class)
    public ResponseEntity<String> handleCancelFavoriteFailure(handleCancelFavoriteFailure e) {
        return ResponseEntity.status(BAD_REQUEST_ERROR).body(e.getMessage());
    }
    
    @ExceptionHandler(handleAddFavoriteFailure.class)
    public ResponseEntity<String> handleAddOrCancelFavoriteFailure(handleAddFavoriteFailure e) {
        return ResponseEntity.status(BAD_REQUEST_ERROR).body(e.getMessage());
    }
    
    @ExceptionHandler(EvaluationNotPermittedException.class)
    public ResponseEntity<?> EvaluationNotPermittedException(EvaluationNotPermittedException e) {
        return ResponseEntity.status(CONFILCT_ERROR).body("아직 평가할 수 없습니다." + e.getMessage());
    }





}
