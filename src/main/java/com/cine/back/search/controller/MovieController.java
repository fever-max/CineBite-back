package com.cine.back.search.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.back.search.dto.MovieDTO;
import com.cine.back.search.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController implements MovieControllerDocs {
    
    private final MovieService movieService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<MovieDTO>> searchByKeyword(@PathVariable String keyword) {
        log.info("특정 검색한 영화 반환 컨트롤러 실행");

        List<MovieDTO> searchResults = movieService.searchByKeyword(keyword);

        // ResponseEntity에 검색 결과와 HTTP 상태 코드 설정 후 반환
        return ResponseEntity.ok(searchResults);
    }


}
