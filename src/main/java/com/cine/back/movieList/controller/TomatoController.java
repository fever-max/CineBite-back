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
    public ResponseEntity<?> rateMovie(@RequestBody Evaluation evaluation,
            @PathVariable(value = "movieId") int movieId) {
        try {
            log.info("[POST][/movie/{}/rate] - 평가 저장", movieId);
            EvaluateResponse response = evaluateService.rateMovie(movieId, evaluation);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{movieId}/deleteRating")
    public ResponseEntity<?> deleteRating(
            @RequestParam(value = "userId") String userId,
            @PathVariable(value = "movieId") int movieId) {
        try {
            log.info("[DELETE][/movie/{}/deleteRating] - 평가 삭제", movieId);
            evaluateService.deleteRating(userId, movieId);
            return ResponseEntity.ok().body("평가가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}