package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.MovieDTO;
import com.cine.back.search.dto.SearchDTO;
import com.cine.back.search.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController implements MovieControllerDocs {
    
    private final MovieService movieService;

    // @GetMapping("/findAll")
    // public ResponseEntity<List<MovieEntity>> getMovieInfo() {
    //     log.info("영화 전체 정보 반환 컨트롤러 실행");

    //     try {
    //         List<MovieEntity> movieEntity = movieService.getMovieInfo();
    //         return ResponseEntity.ok(movieEntity);
    //     } catch (Exception e) {
    //         log.error("영화 전체 정보 반환 실패", e);
    //         return ResponseEntity.status(500).body(null); // 500 Internal Server Error
    //     }
    // }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDTO>> searchByKeyword(@PathVariable String keyword) {
        log.info("검색한 영화 정보 반환 컨트롤러 실행");

        // 검색 결과 반환
        List<MovieDTO> searchResults = movieService.searchByKeyword(keyword);

        // ResponseEntity에 검색 결과와 HTTP 상태 코드 설정 후 반환
        return ResponseEntity.ok(searchResults);
    }


}
