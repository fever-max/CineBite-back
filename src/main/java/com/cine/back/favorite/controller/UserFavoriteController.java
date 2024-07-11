package com.cine.back.favorite.controller;

import org.springframework.web.bind.annotation.*;

import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.service.UserFavoriteService;
import com.cine.back.paging.PageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/favorites")
public class UserFavoriteController implements UserFavoriteControllerDocs{

    private final UserFavoriteService userFavoriteService;
    private final PageService pageService;

    @Override
    @PostMapping("/add")
    public ResponseEntity<Optional<FavoriteResponseDto>> saveToFavorite(@RequestBody FavoriteRequestDto favoriteDto) {
        log.info("[POST][/favorites/add] - 찜 작업 : {}", favoriteDto);
        Optional<FavoriteResponseDto> responseDto = userFavoriteService.addFavorite(favoriteDto);
        log.info("찜하기 컨트롤러 성공!!! 찜한 영화번호 : {}", favoriteDto.movieId());
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteToFavorite(@RequestParam String userId, @RequestParam int movieId) {
        log.info("[DELETE][/favorites/delete] - 찜이 취소된 영화번호 : {}", movieId);
        userFavoriteService.deleteFavorite(userId, movieId);
        log.info("찜목록에서 삭제 컨트롤러 완료!!!");
        return ResponseEntity.ok().body("찜 목록에서 제거 완료!");
    }
    
    @Override
    @GetMapping("/list")
    public ResponseEntity<List<FavoriteResponseDto>> getToFavoriteList(@RequestParam String userId) {
        log.info("[GET][/favorites/list] - 찜 목록 조회 : {}", userId);
        List<FavoriteResponseDto> favoriteDto = userFavoriteService.favoriteList(userId);
        log.info("전체 찜목록 반환 : {}", favoriteDto);
        return ResponseEntity.ok().body(favoriteDto);
    }

    @GetMapping("/paging/favoriteList")
    public ResponseEntity<Page<FavoriteResponseDto>> getToFavoriteListPaged(@RequestParam(value ="userId") String userId, Pageable pageable) {
        log.info("[GET][/favorites/paged-list] - 페이징된 찜 목록 조회");
        Page<FavoriteResponseDto> favoriteDtoPage = pageService.getPagedList(userId, pageable);
        return ResponseEntity.ok().body(favoriteDtoPage);
    }
}