package com.cine.back.search.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.cine.back.search.dto.MovieDTO;
import com.cine.back.search.entity.MovieEntity;
import com.cine.back.search.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    
    private final MovieRepository movieRepository;

    //영화 정보 불러오기
    // public List<MovieEntity> getMovieInfo() {
    //     return movieRepository.findAll();
    // }


  // 키워드를 포함하는 영화명이나 배우 검색
    public List<MovieDTO> searchByKeyword(String keyword) {

        log.info("받은 검색어: {} ", keyword);

        // DB에서 검색 결과 반환
        List<MovieEntity> searchResults = movieRepository.findByMovieNmContainingOrActorsContaining(keyword, keyword);
       
        // 검색결과 DTO로 변환하여 반환
        List<MovieDTO> movieList = new ArrayList<>();
        for(MovieEntity movieEntity  : searchResults) {
            movieList.add(convertToDTO(movieEntity));
        }

        return movieList;
    }

    
    // 검색 결과를 DTO로 변환하는 메서드
    private MovieDTO convertToDTO(MovieEntity movieEntity) {
       
        log.info("DTO로 변환: {}", movieEntity);
       
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setMovieNm(movieEntity.getMovieNm());
        movieDTO.setGenres(movieEntity.getGenres());
        movieDTO.setActors(movieEntity.getActors());
        
        log.info("변환된 DTO: {}", movieDTO);
        
        return movieDTO;
    }

}
