package com.cine.back.movieList.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.response.TrendMovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
@Component
public class ApiCall {

    @Value("${movieList.access-token}")
    private String accessToken;

    @Value("${movieList.urlList}")
    private String urlList;

    @Value("${movieDetail.urlHead}")
    private String urlHead;

    @Value("${movieDetail.urlTail}")
    private String urlTail;

    private final OkHttpClient client = new OkHttpClient();

    public TrendMovieResponse fetchList(int page) throws IOException {
        Request request = new Request.Builder()
            .url(urlList + "?language=ko-KR&page=" + page)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return parseTrendMovieResponse(responseBody);
        }
    }

    public MovieDetailEntity fetchMovieDetails(int movieId) throws IOException {
        Request request = new Request.Builder()
            .url(urlHead + movieId + urlTail + "?language=ko-KR")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            return parseMovieDetails(responseBody);
        }
    }

    private TrendMovieResponse parseTrendMovieResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, TrendMovieResponse.class);
    }

    private MovieDetailEntity parseMovieDetails(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, MovieDetailEntity.class);
    }
}
