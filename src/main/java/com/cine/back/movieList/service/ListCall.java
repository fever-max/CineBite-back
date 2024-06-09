package com.cine.back.movieList.service;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.response.TrendMovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ListCall {

    @Value("${movieList.access-token}")
    private String accessToken;

    private final TrendMovieRepository trendMovieRepository;
    
    public ListCall(TrendMovieRepository trendMovieRepository) {
        this.trendMovieRepository = trendMovieRepository;
    }
    
    public List<TrendMovieEntity> getAllTrendMovies() {
    List<TrendMovieEntity> allMovies = new ArrayList<>();

    for (int page = 1; page <= 10; page++) {
        TrendMovieResponse trendMovieResponse = getTrendMovieList(page);
            if (trendMovieResponse != null) {
                List<TrendMovieEntity> movies = trendMovieResponse.getResults();
                allMovies.addAll(movies);
            }
        }
        return allMovies;
    }

    public TrendMovieResponse getTrendMovieList(int page) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("https://api.themoviedb.org/3/trending/movie/week?language=ko-KR&page=" + page)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " +accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            TrendMovieResponse trendMovieResponse = parseTrendMovieResponse(responseBody);
            saveTrendMovies(trendMovieResponse.getResults());
            log.info("영화 목록 반환 컨트롤러 확인, trendList : {}", trendMovieResponse);
            
            return trendMovieResponse;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("리스트 반환 실패2 : ", e);
            return null;
        }
    }

    // 데이터 저장 
    private void saveTrendMovies(List<TrendMovieEntity> trendMovies) {
        for (TrendMovieEntity movie : trendMovies) {
            trendMovieRepository.save(movie);
        }
    }

    // JSON 문자열을 TrendMovieResponse 객체로 변환
    private TrendMovieResponse parseTrendMovieResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, TrendMovieResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
