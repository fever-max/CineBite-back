package com.cine.back.movieList.service;

import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieListService {

    private final MovieDetailRepository movieDetailRepository;

    // 영화명, 배우, 장르로 검색
    public List<MovieDetailEntity> searchByKeyword(String keyword) {
        log.info("영화 검색 서비스 - 키워드: {}", keyword);
        
        try {
            List<MovieDetailEntity> searchResults = movieDetailRepository.
                findByTitleContainingOrCredits_Cast_NameOrGenres_Name(keyword, keyword, keyword);
        
            if (searchResults.isEmpty()) {
                log.warn("검색 결과 없음 - '{}'에 대한 영화를 찾지 못했습니다.", keyword);
                // 검색 결과가 없을 경우 비슷한 장르의 영화 추천
                return recommendSimilarGenre(keyword);
            }

            return searchResults;
        } catch (Exception e) {
            log.error("영화 검색 서비스 - 키워드 검색 실패", e);
            return List.of(); 
        }
    }

    // 비슷한 장르 영화 추천
    public List<MovieDetailEntity> recommendSimilarGenre(String genre) {
        log.info("영화 추천 서비스 - 같은 장르 영화 추천: {}", genre);
        
        try {
            return movieDetailRepository.findByGenres_NameOrderByReleaseDateDesc(genre);
        } catch (Exception e) {
            log.error("영화 추천 서비스 - 같은 장르 영화 추천 실패", e);
            return List.of();
        }
    }
}
