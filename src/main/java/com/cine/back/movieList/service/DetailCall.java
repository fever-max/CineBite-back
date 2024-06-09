package com.cine.back.movieList.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.entity.movieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.response.MovieDTO;
import com.cine.back.movieList.response.TrendMovieResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
@Service
public class DetailCall {

    @Value("${movieDetail.detail1}")
    private String detailHead;

    @Value("${movieDetail.detail2}")
    private String detailTail;

    @Value("${movieList.access-token}")
    private String accessToken;

    private final MovieDetailRepository movieDetailRepository;

    public DetailCall(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    public movieDetailEntity getMovieDetail(int movieId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" +movieId+ "?append_to_response=credits&language=ko-KR")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return parseMovieDetailResponse(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("상세 목록 반환 실패 : ", e);
            return null;
        }
    }

    // 데이터 저장
    private void saveMovieDetail(movieDetailEntity movie) {
        movieDetailRepository.save(movie);
    }

    // JSON 문자열을 movieDetailEntity 객체로 변환
    private movieDetailEntity parseMovieDetailResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(responseBody, movieDetailEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

