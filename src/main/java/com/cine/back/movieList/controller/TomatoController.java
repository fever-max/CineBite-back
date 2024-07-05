package com.cine.back.movieList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.dto.Evaluation;
import com.cine.back.movieList.response.EvaluateResponse;
import com.cine.back.movieList.service.EvaluateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movie")
public class TomatoController implements TomatoControllerDocs {

    private final EvaluateService evaluateService;

    public TomatoController(EvaluateService evaluateService) {
        this.evaluateService = evaluateService;
    }

    @PostMapping("/{movieId}/rate")
    public ResponseEntity<?> rateMovie(@RequestBody Evaluation evaluation) {
        try {
            System.out.println("하이테스트");
            EvaluateResponse response = evaluateService.rateMovie(evaluation);
            log.info("응답 테스트 : {}", response);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{movieId}/deleteRating")
    public ResponseEntity<?> deleteRating(
                @RequestParam String userId,
                @PathVariable int movieId) {
        try {
            evaluateService.deleteRating(userId, movieId);
            log.info("삭제된 평가정보 : {}", userId, movieId);
            return ResponseEntity.ok().body("평가가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}