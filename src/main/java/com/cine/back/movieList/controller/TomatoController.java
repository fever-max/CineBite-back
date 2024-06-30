package com.cine.back.movieList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.movieList.service.EvaluateService;

@RestController
@RequestMapping("/movies")
public class TomatoController {

    @Autowired
    private EvaluateService evaluateService;

    @PostMapping("/{movieId}/rate")
    public ResponseEntity<String> rateMovie(
            @RequestParam String userId,
            @PathVariable int movieId,
            @RequestParam String rating) {
        try {
            evaluateService.rateMovie(movieId, userId, rating);
            return ResponseEntity.ok("평가가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}