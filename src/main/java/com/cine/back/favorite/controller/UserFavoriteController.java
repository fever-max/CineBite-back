package com.cine.back.favorite.controller;

import org.springframework.web.bind.annotation.*;

import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.service.UserFavoriteService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/favorites")
public class UserFavoriteController implements UserFavoriteControllerDocs{

    private final UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserFavoriteService userFavoriteService) {
        this.userFavoriteService = userFavoriteService;
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<Optional<FavoriteResponseDto>> saveToFavorite(@RequestBody FavoriteRequestDto favoriteDto) {
        log.info("찜한 내용 : {}", favoriteDto);
        Optional<FavoriteResponseDto> responseDto = userFavoriteService.addFavorite(favoriteDto);
        log.info("찜하기 컨트롤러 성공!!! 찜한 영화번호 : {}", favoriteDto.movieId());
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteToFavorite(@RequestParam String userId, @RequestParam int movieId) {
        log.info("삭제한 내용 : {}", movieId);
        userFavoriteService.deleteFavorite(userId, movieId);
        log.info("찜목록에서 삭제 컨트롤러 완료!!!");
        return ResponseEntity.ok().body("찜 목록에서 제거 완료!");
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<FavoriteResponseDto>> getToFavorite(@RequestParam String userId) {
        List<FavoriteResponseDto> favoriteDto = userFavoriteService.favoriteList(userId);
        log.info("전체 찜목록 반환 : {}", favoriteDto);
        return ResponseEntity.ok().body(favoriteDto);
    }
}
