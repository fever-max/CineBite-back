package com.cine.back.recommendation.controller;

import com.cine.back.recommendation.dto.RecommendationRequest;
import com.cine.back.recommendation.service.RecommendationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RecommendationController implements RecommendationControllerDocs{

    private final RecommendationService recommendationService;

    @Override
    @GetMapping("/recommendations")
    public List<RecommendationRequest> getRecommendations(@RequestParam String userId) {
        log.info("# [GET][/recommendations] 유저 {}의 찜목록 컨트롤러 응답 ", userId);
        return recommendationService.recommendMovies(userId);
    }
}
