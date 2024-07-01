package com.cine.back.recommendation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.recommendation.entity.UserFavorite;
import com.cine.back.recommendation.service.UserFavoriteService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/favorites")
public class UserFavoriteController implements UserFavoriteControllerDocs {
    
    private final UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserFavoriteService userFavoriteService) {
        this.userFavoriteService = userFavoriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToFavorite(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "movieId") int movieId) 
        {
        userFavoriteService.addFavorite(userId, movieId);
        log.info("찜하기 추가 컨트롤러 완료!!!");
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteToFavorite (
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "movieId") int movieId) 
        {
        userFavoriteService.deleteFavorite(userId, movieId);
        log.info("찜하기 삭제 컨트롤러 완료!!!");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<List<UserFavorite>>>getToFavorite(
        @PathVariable(value = "userId") String userId) {
            try {
                Optional<List<UserFavorite>> favoriteList = userFavoriteService.favoriteChoice(userId);
                log.info("찜하기 목록 : {}", favoriteList);
                return ResponseEntity.ok().body(favoriteList);
            } catch(Exception e) {
                log.error("찜목록이 없어요 {}", e, userId);
            throw e;
            }
    }
    
}
