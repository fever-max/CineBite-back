package com.cine.back.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.response.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Slf4j
@Component
public class MovieConfig {
    // 영화 목록
    @Value("${movieList.access-token}")
    private String accessToken;

    @Value("${movieList.urlHead}")
    private String urlHead;

    @Value("${movieList.urlTail}")
    private String urlTail;
    
    @Value("${movieList.urlweek}")
    private String week; 
    
    // 상세 정보
    @Value("${movieDetail.urlHead}")
    private String urlDetailHead;
    
    @Value("${movieDetail.urlTail}")
    private String urlDetailTail;

    private final OkHttpClient client = new OkHttpClient();

    // 영화 목록
    public MovieResponse fetchMovieList(int page) throws IOException {
        String url = urlHead + week + urlTail;
        Request request = new Request.Builder()
            .url(url + page)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("에러 - 영화 목록 요청 실패", response.code());
                throw new IOException("잘못된 요청: " + response);
            }

            String responseBody = response.body().string();
            log.debug("영화 목록 응답: {}", responseBody);
            return parseTrendMovieResponse(responseBody);

        }catch (IOException e) {
            log.error("에러 - 영화 목록 요청 중 예외 발생", e);
            throw e;
        }
    }

    // 상세 정보
    public MovieDetailEntity fetchMovieDetails(int movieId) throws IOException {
        Request request = new Request.Builder()
            .url(urlDetailHead + movieId + urlDetailTail)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("에러 - 영화 상세 정보 요청 실패", response.code());
                throw new IOException("잘못된 요청: " + response);
            }

            String responseBody = response.body().string();
            log.debug("영화 상세 정보 응답: {}", responseBody);
            return parseMovieDetails(responseBody);
        }catch (IOException e) {
            log.error("에러 - 영화 상세 정보 요청 중 예외 발생: ", movieId, e);
            throw e;
        }
    }

    private MovieResponse parseTrendMovieResponse(String responseBody) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, MovieResponse.class);
        } catch (IOException e) {
            log.error("에러 - 영화 목록 응답 파싱 중 예외 발생", e);
            throw e;
        }
    }

    private MovieDetailEntity parseMovieDetails(String responseBody) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, MovieDetailEntity.class);
        } catch (IOException e) {
            log.error("에러 - 영화 상세 정보 응답 파싱 중 예외 발생", e);
            throw e;
        }
    }
}
