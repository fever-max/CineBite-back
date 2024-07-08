package com.cine.back.movieList.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.back.config.BoxOfficeConfig;
import com.cine.back.movieList.entity.BoxOfficeMovieEntity;
import com.cine.back.movieList.repository.BoxOfficeMovieRepository;
import com.cine.back.movieList.response.BoxOfficeMovieResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoxOfficeService {
    private final BoxOfficeConfig boxOfficeConfig;
    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    public BoxOfficeService(BoxOfficeConfig boxOfficeConfig, BoxOfficeMovieRepository boxOfficeMovieRepository) {
        this.boxOfficeConfig = boxOfficeConfig;
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
    }

    @PostConstruct
    public void init() {
        try {
            saveBoxOfficeMovieDate();
            log.info("박스오피스 api 데이터 저장 성공");
        } catch (Exception e) {
            log.error("에러 - 박스오피스 api 데이터 자동 저장 실패", e);
        }
    }

    public void saveBoxOfficeMovieDate() {
        BoxOfficeMovieResponse movieResponse = boxOfficeConfig.getBoxOfficeData();
        try {
            if (movieResponse != null) {
                List<BoxOfficeMovieEntity> boxOfficeMovieEntity = movieResponse.getResults().getWeeklyBoxOfficeList();
                log.info("박스오피스 API 응답 개수: {}", boxOfficeMovieEntity.size());
                for (BoxOfficeMovieEntity list : boxOfficeMovieEntity) {
                    saveMovieDetail(list);
                    log.info("박스오피스 영화 저장 성공", list.getMovieCd());
                }
            }
        } catch (Exception e) {
            log.error("에러 - 박스오피스 api 영화 저장 실패", e);
        }
    }

    // 데이터 저장 (중복 방지)
    private void saveMovieDetail(BoxOfficeMovieEntity boxOfficeMovieEntity) {
        try {
            Optional<BoxOfficeMovieEntity> optionalExistingMovie = boxOfficeMovieRepository
                    .findByMovieCd(boxOfficeMovieEntity.getMovieCd());
            if (optionalExistingMovie.isPresent()) {
                BoxOfficeMovieEntity existingMovie = optionalExistingMovie.get();
                existingMovie.setMovieRank(boxOfficeMovieEntity.getMovieRank());
                existingMovie.setRankInTen(boxOfficeMovieEntity.getRankInTen());
                existingMovie.setRankOldAndNew(boxOfficeMovieEntity.getRankOldAndNew());
                boxOfficeMovieRepository.save(existingMovie);
                log.debug("박스오피스 - 기존 영화 정보 수정 및 저장: {}", optionalExistingMovie.get());
            } else {
                boxOfficeMovieRepository.save(boxOfficeMovieEntity);
                log.debug("박스오피스 - 영화 상세정보 추가: {}", boxOfficeMovieEntity);
            }
        } catch (Exception e) {
            log.error("에러 - 박스오피스 저장 실패, 영화 코드: {}", boxOfficeMovieEntity.getMovieCd(), e);
        }
    }
}
