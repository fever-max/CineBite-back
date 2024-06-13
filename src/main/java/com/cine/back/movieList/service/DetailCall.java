package com.cine.back.movieList.service;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@Slf4j
@Service
public class DetailCall {
    @Value("${movieDetail.urlHead}")
    private String urlHead;

    @Value("${movieDetail.urlTail}")
    private String urlTail;

    @Value("${movieList.access-token}")
    private String accessToken;

    private final MovieDetailRepository movieDetailRepository;
    private final Set<Integer> requestedMovieId = new HashSet<>();

    public DetailCall(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    public synchronized Optional<MovieDetailEntity> getMovieDetail(int movieId) {
        OkHttpClient client = new OkHttpClient();
        
        if (requestedMovieId.contains(movieId)) {
            log.info("이미 요청된 영화 상세 정보, movie_id: {}", movieId);
            return Optional.empty();
        }

        String url = urlHead + movieId + urlTail;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

                log.info("영화 상세정보 번호 - \n , movieId : {}", movieId);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("유효하지 않은 데이터 : " + response);
            }
            String responseBody = response.body().string();
            MovieDetailEntity movieDetail = parseMovieDetailResponse(responseBody);
            
        // 중복된 영화번호가 없고 null이 아니라면 가져온 상세 정보를 저장
        if (movieDetail != null) {
            movieDetailRepository.save(movieDetail);
            requestedMovieId.add(movieId);
        }
        log.info("영화 상세 정보 반환 성공 : \n -> movieId : {}", movieDetail);
        return Optional.ofNullable(movieDetail);    // 응답 값이 포함되어 있다면 해당 값을 포함하는 optional 객체 생성

        } catch (IOException e) {
            e.printStackTrace();
            log.error("상세 목록 반환 실패 : ", e);
            return Optional.empty();    // 응답 값이 null 일 경우 비어있는 optional 객체 생성
        }
    }

    // JSON 문자열을 movieDetailEntity 객체로 변환
    private MovieDetailEntity parseMovieDetailResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(responseBody, MovieDetailEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
