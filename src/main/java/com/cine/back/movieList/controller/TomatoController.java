package com.cine.back.movieList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.service.EvaluateService;

@RestController
@RequestMapping("/movies")
public class TomatoController implements TomatoControllerDocs {

    private final EvaluateService evaluateService;

    public TomatoController(EvaluateService evaluateService) {
        this.evaluateService = evaluateService;
    }

    @Override
    @PostMapping("/{movieId}/rate")
    public ResponseEntity<String> rateMovie(
            @RequestParam(value = "userId") String userId,
            @PathVariable(value = "movieId") int movieId,
            @RequestParam(value = "rating") String rating) {
        try {
            evaluateService.rateMovie(movieId, userId, rating);
            return ResponseEntity.ok("평가가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}